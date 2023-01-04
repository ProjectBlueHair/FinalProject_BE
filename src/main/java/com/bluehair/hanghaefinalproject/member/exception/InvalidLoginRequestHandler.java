package com.bluehair.hanghaefinalproject.member.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import com.bluehair.hanghaefinalproject.member.controller.MemberController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(assignableTypes = MemberController.class)
public class InvalidLoginRequestHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { InvalidLoginRequestException.class })
    public ResponseEntity<ErrorResponse> handleInvalidLoginRequest(InvalidLoginRequestException e) {
        log.error("InvalidSignUpRequestException throws : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
