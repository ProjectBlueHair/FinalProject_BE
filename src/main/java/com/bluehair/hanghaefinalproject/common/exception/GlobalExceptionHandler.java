package com.bluehair.hanghaefinalproject.common.exception;

import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final CollaboRequestRepository collaboRequestRepository;
    private final PostRepository postRepository;
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
        log.error("InvalidRequestException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler
    @Transactional
    public ResponseEntity<ErrorResponse> handleInvalidAudioFileException(InvalidAudioFileException e) {
        log.error("InvalidAudioFileException throwed at " + e.getDomain() + "_"+ e.getLayer() + " : " + e.getErrorCode());
        if(e.getCollaboRequest()!=null){
            collaboRequestRepository.delete(e.getCollaboRequest());
        }
        if(e.getPost()!=null){
            postRepository.deleteById(e.getPost().getId());
        }
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
