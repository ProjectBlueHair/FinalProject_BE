package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MainPostDto {
    private Long id;
    private String title;
    private Long viewCount;
    private Long likeCount ;
    private List<String> musicFileList;
    private List<String> profileList;

    @Builder
    public MainPostDto(Long id, String  title, Long likeCount, Long viewCount, List<String> musicFileList
                    ,List<String> profileList){
        this.id = id;
        this.title = title;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.musicFileList = musicFileList;
        this.profileList = profileList;
    }

}
