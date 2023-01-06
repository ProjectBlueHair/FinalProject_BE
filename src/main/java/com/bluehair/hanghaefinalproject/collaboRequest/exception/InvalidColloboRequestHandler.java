package com.bluehair.hanghaefinalproject.collaboRequest.exception;

import com.bluehair.hanghaefinalproject.collaboRequest.controller.CollaboRequestController;
import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(assignableTypes = CollaboRequestController.class)
public class InvalidColloboRequestHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { InvalidCollaboRequestException.class })
    public ResponseEntity<ErrorResponse> handleInvalidCollaboRequest(InvalidCollaboRequestException e) {
        log.error("InvalidCollaboRequestException throws : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
