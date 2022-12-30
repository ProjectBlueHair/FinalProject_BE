package com.bluehair.hanghaefinalproject.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer customHttpStatus;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .customHttpStatus(errorCode.getCustomHttpStatusCode())
                        .message(errorCode.getMessage())
                        .build());
    }
}
