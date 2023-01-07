package com.bluehair.hanghaefinalproject.comment.dto.requestDto;

import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentDto;
import lombok.Getter;

@Getter
public class RequestCommentDto {
    private String contents;

    public CommentDto toCommentDto() {
        return CommentDto.builder()
                .contents(contents)
                .build();
    }
}
