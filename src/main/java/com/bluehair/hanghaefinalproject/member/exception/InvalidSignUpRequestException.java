package com.bluehair.hanghaefinalproject.member.exception;

import com.bluehair.hanghaefinalproject.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidSignUpRequestException extends RuntimeException{
    private final ErrorCode errorCode;
}
