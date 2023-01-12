package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Getter;

import java.util.List;

@Getter
public class MainProfileDto {
    private List<String> musicPartList;
    private String profileImg;

    private String nickname;

    public MainProfileDto (List<String> musicPartList, String profileImg, String nickname){
        this.musicPartList = musicPartList;
        this.profileImg = profileImg;
        this.nickname = nickname;
    }

}
