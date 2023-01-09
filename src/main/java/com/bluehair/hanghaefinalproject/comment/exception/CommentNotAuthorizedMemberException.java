package com.bluehair.hanghaefinalproject.comment.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentNotAuthorizedMemberException extends RuntimeException {
    private final ErrorCode errorCode;
}

