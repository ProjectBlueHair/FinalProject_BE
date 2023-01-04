package com.bluehair.hanghaefinalproject.post.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundPostRequestException extends RuntimeException{
    private final ErrorCode errorCode;
}
