package com.bluehair.hanghaefinalproject.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Schema(description = "요청 성공 시 ResponseDto")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    @Schema(description = "Custom Http 상태 코드", example = "2000")
    private final Integer customHttpStatus;
    @Schema(description = "상태 메세지", example = "~기능 성공")
    private final String message;
    @Schema(description = "반환 데이터")
    private T data;

    public static<T> ResponseEntity<SuccessResponse<T>> toResponseEntity(SucessCode sucessCode, T data){
        return ResponseEntity
                .status(sucessCode.getHttpStatus())
                .body(SuccessResponse.<T>builder()
                        .customHttpStatus(sucessCode.getCustomHttpStatusCode())
                        .message(sucessCode.getMessage())
                        .data(data)
                        .build());
    }
}
