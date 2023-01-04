package com.bluehair.hanghaefinalproject.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private final Integer customHttpStatus;
    private final String message;
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
