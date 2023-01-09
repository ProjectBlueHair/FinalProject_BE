package com.bluehair.hanghaefinalproject.comment.exception;

import com.bluehair.hanghaefinalproject.comment.controller.CommentController;
import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(assignableTypes = CommentController.class)
public class CommentNotAuthorizedMemberHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CommentNotAuthorizedMemberException.class})
    public ResponseEntity<ErrorResponse> handleInvalidMember(CommentNotAuthorizedMemberException e) {
        log.error("InvalidMemberException throws : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}