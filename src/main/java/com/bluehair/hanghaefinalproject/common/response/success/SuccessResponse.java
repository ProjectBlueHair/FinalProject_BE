package com.bluehair.hanghaefinalproject.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    @ApiModelProperty(notes = "커스텀 HTTP 상태 코드", example = "2000")
    private final Integer customHttpStatus;
    @ApiModelProperty(notes = "반환 메세지", example = "회원 가입 성공")
    private final String message;
    @ApiModelProperty(notes = "반환 데이터", example = "반환 데이터")
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
