package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class  CollaboRequestDto {
    private String title;
    private String contents;
    private String nickname;
    private Boolean activated;
    private Boolean approval;


    @Builder
    public CollaboRequestDto(CollaboRequestDetailsDto collaboRequestDetailsDto, String nickname) {
        this.title = collaboRequestDetailsDto.getTitle();
        this.contents = collaboRequestDetailsDto.getContents();
        this.nickname = nickname;
        this.activated = true;
        this.approval = false;


    }
}
