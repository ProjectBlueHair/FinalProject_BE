package com.bluehair.hanghaefinalproject.common.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import com.bluehair.hanghaefinalproject.member.controller.MemberController;
import com.bluehair.hanghaefinalproject.security.exception.CustomJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = MemberController.class)
public class TokenReissuanceExceptionHandler {
    @ExceptionHandler(value = { CustomJwtException.class })
    public ResponseEntity<ErrorResponse> handleTokenReissuance(CustomJwtException e) {
        log.error("CustomJwtException throws : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
