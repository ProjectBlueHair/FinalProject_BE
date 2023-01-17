package com.bluehair.hanghaefinalproject.common.exception;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.common.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidAudioFileException extends IllegalArgumentException {
    private Domain domain;
    private Layer layer;
    private ErrorCode errorCode;
    private CollaboRequest collaboRequest;
}
