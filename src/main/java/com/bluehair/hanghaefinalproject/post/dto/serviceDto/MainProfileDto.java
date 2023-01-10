package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Getter;

import java.util.List;

@Getter
public class MainProfileDto {
    private List<String> musicPartList;
    private String profileImg;


    public MainProfileDto (List<String> musicPartList, String profileImg){
        this.musicPartList = musicPartList;
        this.profileImg = profileImg;
    }

}
