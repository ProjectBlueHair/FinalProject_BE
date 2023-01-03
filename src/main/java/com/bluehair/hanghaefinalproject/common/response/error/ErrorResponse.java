package com.bluehair.hanghaefinalproject.common.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    @Schema(description = "에러 발생 시각", example = "LocalDateTime")
    private final String timestamp = LocalDateTime.now().toString();
    @Schema(description = "커스텀 HTTP 상태코드", example = "4091")
    private final Integer customHttpStatus;
    @Schema(description = "에러 메세지", example = "중복된 이메일입니다.")
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
