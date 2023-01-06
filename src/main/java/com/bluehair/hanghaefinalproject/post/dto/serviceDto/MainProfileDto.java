package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Getter;
@Getter
public class MainProfileDto {
    private String musicPart;
    private String profileImg;


    public MainProfileDto (String musicPart, String profileImg){
        this.musicPart = musicPart;
        this.profileImg = profileImg;
    }

}
