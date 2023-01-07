package com.bluehair.hanghaefinalproject.comment.dto.serviceDto;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {

    private String contents;


    @Builder
    public CommentDto(String profileImg,String nickname,String contents, Long parentsId ,Post post){
        this.contents = contents;
    }
}
