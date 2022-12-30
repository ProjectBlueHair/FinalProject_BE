package com.bluehair.hanghaefinalproject.member.exception;

import com.bluehair.hanghaefinalproject.common.exception.ErrorResponse;
import com.bluehair.hanghaefinalproject.member.controller.MemberController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(assignableTypes = MemberController.class)
public class InvalidSignUpRequestHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { InvalidSignUpRequestException.class })
    public ResponseEntity<ErrorResponse> handleInvalidSignUpRequest(InvalidSignUpRequestException e) {
        log.error("InvalidSignUpRequestException throws : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
