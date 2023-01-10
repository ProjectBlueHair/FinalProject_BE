package com.bluehair.hanghaefinalproject.common.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotAuthorizedMemberException(NotAuthorizedMemberException e) {
        log.error("NotAuthorizedMemberException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleFormatException(FormatException e) {
        log.error("FormatException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDuplicationException(DuplicationException e) {
        log.error("DuplicationException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException e) {
        log.error("InvalidException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
