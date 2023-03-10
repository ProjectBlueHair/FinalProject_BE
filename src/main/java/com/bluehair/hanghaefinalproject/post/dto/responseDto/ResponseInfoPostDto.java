package com.bluehair.hanghaefinalproject.post.dto.responseDto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "상세 조회 응답 Dto")
@Getter
public class ResponseInfoPostDto {
    @Schema(description = "게시글 제목", example = "게시글 제목")
    private String title;
    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String contents;
    @Schema(description = "게시글 콜라보 요청사항", example = "콜라보 요청사항")
    private String collaboNotice;
    @Schema(description = "게시글 이미지", example = "게시글 이미지")
    private String postImg;
    @Schema(description = "게시글 좋아요 수", example = "게시글 좋아요 수")
    private Long likeCount;
    @Schema(description = "게시글 좋아요 여부", example = "true")
    private Boolean isLiked;
    @Schema(description = "게시글 조회수", example = "게시글 조회수")
    private Long viewCount;
    @Schema(description = "태그 리스트", example = "태그 리스트")
    private List<String> tagList = new ArrayList<>();
    @Schema(description = "생성 시간", example = "2022-12-31")
    private String createdAt;

    @Builder
    public ResponseInfoPostDto(Post post, Boolean isLiked){
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.collaboNotice = post.getCollaboNotice();
        this.postImg = post.getPostImg();
        this.isLiked = isLiked;
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
        for (Tag tag : post.getTagList()) {
            this.tagList.add(tag.getContents());
        }
        this.createdAt = LocalDateTimeConverter.timeToString8digits(post.getCreatedAt());
    }
}
