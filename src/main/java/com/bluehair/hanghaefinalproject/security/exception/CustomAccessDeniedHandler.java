package com.bluehair.hanghaefinalproject.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_AUTHORIZED;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws CustomJwtException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.warn("User : " + auth.getPrincipal()
                    + "attemped to access the protect URL "
                    + request.getRequestURI()
            );
        }
        throw new CustomJwtException(MEMBER_NOT_AUTHORIZED);
    }
}
