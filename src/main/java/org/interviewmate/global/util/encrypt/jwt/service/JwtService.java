package org.interviewmate.global.util.encrypt.jwt.service;

import static org.interviewmate.global.error.ErrorCode.EXPIRED_TOKEN;
import static org.interviewmate.global.error.ErrorCode.FAIL_TO_CREATE;
import static org.interviewmate.global.error.ErrorCode.FAIL_TO_LOAD_SUBJECT;
import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_USER;
import static org.interviewmate.global.util.encrypt.Secret.JWT_KEY;
import static org.interviewmate.global.util.encrypt.jwt.model.TokenType.ACCESS_TOKEN;
import static org.interviewmate.global.util.encrypt.jwt.model.TokenType.REFRESH_TOKEN;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.auth.model.dto.request.ReissueAccessTokenReq;
import org.interviewmate.domain.auth.model.dto.response.LoginRes;
import org.interviewmate.domain.auth.model.dto.response.ReissueAccessTokenRes;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.Authority;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.util.encrypt.jwt.model.Subject;
import org.interviewmate.global.util.encrypt.jwt.model.TokenType;
import org.interviewmate.global.util.encrypt.security.exception.SecurityException;
import org.interviewmate.global.util.encrypt.security.service.CustomUserDetailsService;
import org.interviewmate.infra.redis.repository.RedisTokenRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {

    private final RedisTokenRepository redisService;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 로그인 시 토큰 발행
     */
    public LoginRes issueTokenByLogin(User user) {

        log.info("-----Start To Create Token-----");

        String email = user.getEmail();
        List<Authority> roles = user.getRoles();

        String accessToken = issueToken(email, roles, ACCESS_TOKEN);

        if (Objects.isNull(redisService.findByKey(email))) {
            String refreshToken = issueToken(email, roles, REFRESH_TOKEN);
            redisService.saveWithExpirationDate(email, refreshToken, REFRESH_TOKEN.getExpirationTime());
        }

        log.info("-----Complete To Create Token-----");

        return LoginRes.of(user, accessToken);

    }

    /**
     * 토큰 발행
     * Token Type은 매개 변수를 통해 받아 지정
     */
    private String issueToken(String email, List<Authority> roles, TokenType type) {

        Date now = new Date();
        Claims claims = setClaims(email, type, roles);
        Date expirationDate = getExpirationDate(now, type.getExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }

    private Claims setClaims(String email, TokenType type, List<Authority> roles) {

        log.info("Type");
        log.info("-> " + type.getName());
        log.info("Email Information");
        log.info("-> " + email);
        log.info("Role Information" );
        roles.forEach(role -> log.info("-> " + role.getName()));

        Claims claims;
        Subject subject = Subject.builder()
                .email(email)
                .type(type).build();

        try {
            claims = Jwts.claims().setSubject(objectMapper.writeValueAsString(subject));
            claims.put("roles", roles);
        } catch (JsonProcessingException e) {
            throw new SecurityException(FAIL_TO_CREATE);
        }

        return claims;

    }


    private Date getExpirationDate(Date now, long expirationTime) {

        Date expirationDate = new Date(now.getTime() + expirationTime);

        log.info("Expiration Date");
        log.info("-> " + expirationDate);

        return expirationDate;

    }

    /**
     * 토큰 재발급 (Refresh Token 검증 후)
     */
    public ReissueAccessTokenRes reissueAccessToken(ReissueAccessTokenReq reissueAccessTokenReq) {

        User finduser = userRepository
                .findByEmail(reissueAccessTokenReq.getEmail())
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        String findToken = redisService.findByKey(finduser.getEmail());

        if (Objects.isNull(findToken)) {
            throw new SecurityException(EXPIRED_TOKEN);
        }

        return ReissueAccessTokenRes.of(
                finduser,
                issueToken(finduser.getEmail(), finduser.getRoles(), ACCESS_TOKEN)
        );

    }

    /**
     * 토큰 가져오기
     */
    public String getJwtFromReqHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    /**
     * Subject 추출
     */
    public Subject getSubject(String token) {

        String subject = getClaims(token).getBody().getSubject();

        try {
            return objectMapper.readValue(subject, Subject.class);
        } catch (JsonProcessingException e) {
            throw new SecurityException(FAIL_TO_LOAD_SUBJECT);
        }

    }

    /**
     *  payload 추출
     */
    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token.substring(token.indexOf(" ")).trim());
    }

    /**
     * Authentication 추출
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSubject(token).getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {

        try {
            getClaims(token);
            return true;
        }  catch (ExpiredJwtException exception) {
            log.error("만료된 토큰입니다.");
        } catch (JwtException exception) {
            log.error("유효하지 않은 토큰입니다.");
        } catch (NullPointerException exception) {
            log.error("토큰이 없습니다.");
        }

        return false;

    }

}
