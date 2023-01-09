package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import lombok.Builder;
import lombok.Getter;

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

    public CollaboRequestDto(CollaboRequestDetailsDto collaboRequestDetailsDto){
        this.title = collaboRequestDetailsDto.getTitle();
        this.contents = collaboRequestDetailsDto.getContents();
    }
}
