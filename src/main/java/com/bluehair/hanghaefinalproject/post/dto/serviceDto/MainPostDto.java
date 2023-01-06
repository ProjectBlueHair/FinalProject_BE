package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Schema(description = "전체 조회 응답 Dto")
@Getter
public class MainPostDto {
    private Long id;
    private String title;
    private Long viewCount;
    private Long likeCount;
    private List<String> musicFileList;
    private List<MainProfileDto> mainProfileDtos;

    @Builder
    public MainPostDto(Long id, String  title, Long likeCount, Long viewCount, List<String> musicFileList, List<MainProfileDto> mainProfileDtos){
        this.id = id;
        this.title = title;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.musicFileList = musicFileList;
        this.mainProfileDtos = mainProfileDtos;
    }

}
