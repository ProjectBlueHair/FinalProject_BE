package com.bluehair.hanghaefinalproject.security.jwt;

import com.bluehair.hanghaefinalproject.security.exception.CustomJwtException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.UNHANDLED_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, CustomJwtException {
        String token = jwtUtil.resolveToken(request, "AccessToken");

        if (token == null || request.getRequestURI().equals("/api/member/reissuance")){
            filterChain.doFilter(request, response);
            return;
        }

        if(!jwtUtil.validateToken(token, true)){
            throw new CustomJwtException(UNHANDLED_SERVER_ERROR);
        }

        Claims info = jwtUtil.getUserInfoFromHttpServletRequest(request, true);
        setAuthentication(info.getSubject());

        filterChain.doFilter(request,response);
    }

    private void setAuthentication(String memberName) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(memberName);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
