package com.bluehair.hanghaefinalproject.security.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidMemberException extends RuntimeException{
    private final ErrorCode errorCode;
}
