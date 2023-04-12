package org.interviewmate.global.util.encrypt.jwt.service;

import static org.interviewmate.global.error.ErrorCode.FAIL_TO_CREATE;
import static org.interviewmate.global.util.encrypt.Secret.JWT_KEY;

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
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.user.model.Authority;
import org.interviewmate.global.util.encrypt.jwt.model.Subject;
import org.interviewmate.global.util.encrypt.jwt.model.TokenType;
import org.interviewmate.global.util.encrypt.security.exception.SecurityException;
import org.interviewmate.global.util.encrypt.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService userDetailsService;

    private Key secretKey;

    /**
     *  salt를 사용하여 한번 더 암호화 진행
     */
    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Token 생성
     */
    public String createToken(String email, List<Authority> roles, TokenType type) {

        log.info("-----Start To Create Token-----");
        log.info("Type");
        log.info("-> " + type.getName());

        String token = Jwts.builder()
                .setClaims(getClaims(email, type, roles))
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate(type.getExpirationTime()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("-----Complete To Create Token-----");

        return token;

    }

    private Claims getClaims(String email, TokenType type, List<Authority> roles) {

        log.info("Email Information");
        log.info("-> " + email);
        log.info("Role Information" );
        roles.forEach(role -> log.info("-> " + role.getName()));

        Claims claims = null;
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


    private static Date getExpirationDate(long expirationTime) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        log.info("Expiration Date");
        log.info("-> " + expirationDate);

        return expirationDate;

    }

    /**
     * Token에서 이메일 정보 추출
     */
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Token에서 권한 정보 추출
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰 인증 (Authorization 헤더 사용)
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * Token 추출
     */
    public Jws<Claims> parseJwt(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token.substring(token.indexOf(" ")).trim());
        return claims;
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = parseJwt(token);
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
