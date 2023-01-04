package com.bluehair.hanghaefinalproject.post.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import com.bluehair.hanghaefinalproject.post.controller.PostController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@Slf4j
@RestControllerAdvice(assignableTypes = PostController.class)
public class NotFoundPostRequestHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { NotFoundPostRequestException.class })
    public ResponseEntity<ErrorResponse> handleNotFoundPostRequest(NotFoundPostRequestException e) {
        log.error("NotFoundPostRequestException throws : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}

