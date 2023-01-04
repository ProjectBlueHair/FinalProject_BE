package com.bluehair.hanghaefinalproject.member.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidSignUpRequestException extends IllegalArgumentException {
    private final ErrorCode errorCode;
}
