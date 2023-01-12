package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Schema(description = "전체 조회 응답 Dto")
@Getter
public class MainPostDto {
    @Schema(description = "게시글 번호", example = "게시글 번호")
    private Long id;
    @Schema(description = "게시글 제목", example = "게시글 제목")
    private String title;
    @Schema(description = "게시글 이미지", example = "게시글 이미지")
    private String postImg;
    @Schema(description = "게시글 조회수", example = "게시글 조회수")
    private Long viewCount;
    @Schema(description = "게시글 좋아요 수", example = "게시글 좋아요 수")
    private Long likeCount;
    @Schema(description = "댓글 번호", example = "댓글 번호")
    private String musicFile;
    @Schema(description = "태그 리스트", example = "태그 리스트")
    private List<String> tagList;
    @Schema(description = "댓글 번호", example = "댓글 번호")
    private List<MainProfileDto> mainProfileList;

    @Builder
    public MainPostDto(Long id, String title, String postImg, Long likeCount, Long viewCount, String musicFile, List<String> tagList,List<MainProfileDto> mainProfileList){
        this.id = id;
        this.title = title;
        this.postImg = postImg;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.musicFile = musicFile;
        this.tagList = tagList;
        this.mainProfileList = mainProfileList;
    }

}
