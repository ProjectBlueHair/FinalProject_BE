package com.bluehair.hanghaefinalproject.member.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidLoginRequestException extends SecurityException{
    private final ErrorCode errorCode;
}
