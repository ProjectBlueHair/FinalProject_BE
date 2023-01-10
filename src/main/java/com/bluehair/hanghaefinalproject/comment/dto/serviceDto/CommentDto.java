package com.bluehair.hanghaefinalproject.comment.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {

    private String contents;

    @Builder
    public CommentDto(String contents){
        this.contents = contents;
    }
}
