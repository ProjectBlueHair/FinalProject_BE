package com.bluehair.hanghaefinalproject.security.jwt;

import com.bluehair.hanghaefinalproject.member.entity.MemberRole;
import com.bluehair.hanghaefinalproject.security.exception.CustomJwtException;
import com.bluehair.hanghaefinalproject.security.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;

@Slf4j
@Component
@PropertySource("classpath:application-jwt.properties")
@RequiredArgsConstructor
public class JwtUtil {
    public static final String AUTHORIZATION_ACCESS = "AccessToken";
    public static final String AUTHORIZATION_REFRESH = "RefreshToken";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 1000L;
    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret.key.access}")
    private String accessTokenSecretKey;
    @Value("${jwt.secret.key.refresh}")
    private String refreshTokenSecretKey;

    private Key accessTokenKey;
    private Key refreshTokenKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] accessTokenBytes = Base64.getDecoder().decode(accessTokenSecretKey);
        accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);

        byte[] refreshTokenBytes = Base64.getDecoder().decode(refreshTokenSecretKey);
        refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenBytes);
    }

    public String resolveToken(HttpServletRequest request, String authorization) {
        String bearerToken = request.getHeader(authorization);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createAccessToken(String memberEmail, MemberRole role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(memberEmail)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(accessTokenKey, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken() {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME + 30 * 60 * 1000L))
                        .setIssuedAt(date)
                        .signWith(refreshTokenKey, signatureAlgorithm)
                        .compact();
    }

    public boolean validateToken(String token, Boolean isAccess) throws CustomJwtException{
        try {
            if(isAccess){
                Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token);
            }
            if(!isAccess){
                Jwts.parserBuilder().setSigningKey(refreshTokenKey).build().parseClaimsJws(token);
            }
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            if(isAccess){
                throw new CustomJwtException(INVALID_ACCESSTOKEN);
            }
            throw new CustomJwtException(INVALID_REFRESHTOKEN);
        } catch (ExpiredJwtException e) {
            if(isAccess){
                throw new CustomJwtException(EXPIRED_ACCESSTOKEN);
            }
            throw new CustomJwtException(EXPIRED_REFRESHTOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT, 지원되지 않는 Refresh JWT 입니다.");
        } catch (IllegalArgumentException e) {
            if(isAccess){
                throw new CustomJwtException(ACCESSTOKEN_NOT_EXIST);
            }
            throw new CustomJwtException(REFRESHTOKEN_NOT_EXIST);
        }
        return false;
    }
    public Claims getUserInfoFromHttpServletRequest(HttpServletRequest request, Boolean isAccess) throws CustomJwtException{
        if(isAccess){
            String token = resolveToken(request, AUTHORIZATION_ACCESS);
            try {
                return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody();
            }
            catch (ExpiredJwtException ex) {
                return ex.getClaims();
            }
        }
        String token = resolveToken(request, AUTHORIZATION_REFRESH);
        return Jwts.parserBuilder().setSigningKey(refreshTokenKey).build().parseClaimsJws(token).getBody();
    }
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
