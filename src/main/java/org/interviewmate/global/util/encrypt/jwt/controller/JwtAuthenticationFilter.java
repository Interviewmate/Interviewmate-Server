package org.interviewmate.global.util.encrypt.jwt.controller;

import static org.interviewmate.global.error.ErrorCode.*;
import static org.interviewmate.global.util.encrypt.jwt.model.TokenType.REFRESH_TOKEN;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.interviewmate.global.util.encrypt.jwt.model.Subject;
import org.interviewmate.global.util.encrypt.jwt.service.JwtService;
import org.interviewmate.global.util.encrypt.security.exception.SecurityException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 헤더에서 토큰 가져오기
        String token = jwtService.getJwtFromReqHeader(request);

        if (token != null && jwtService.validateToken(token)) { // 토큰이 존재하면서 유효한 경우만 인증 허가

            // refresh 토큰 확인
            Subject subject = jwtService.getSubject(token);
            String requestURI = request.getRequestURI();

            if (subject.getType().equals(REFRESH_TOKEN) && !requestURI.equals("/auth/reissue")) {
                throw new SecurityException(INVALID_TOKEN_TYPE);
            }

            // 권한 추출
            Authentication auth = jwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filterChain.doFilter(request, response);

    }

}
