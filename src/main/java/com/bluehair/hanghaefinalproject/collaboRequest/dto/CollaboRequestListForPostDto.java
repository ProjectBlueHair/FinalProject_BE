package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "콜라보 리퀘스트 전체 조회 Dto")
@Getter
public class CollaboRequestListForPostDto {
    private Long collaboId;
    @Schema(description = "콜라보 리퀘스트 제목", required = true, example = "콜라보 요청합니다.")
    private String title;
    @Schema(description = "닉네임", example = "test01")
    private String nickname;
    @Schema(description = "프로필 이미지", example = "Profile Img from S3")
    private String profileImg;
    @Schema(description = "작성시간", example = "작성시간")
    private LocalDateTime createdAt;
    @Schema(description = "작성시간", example = "수정시간")
    private LocalDateTime modifiedAt;
    @Schema(description = "콜라보리퀘스트 음악파일 목록", example = "musicPartsList")
    private List<String> musicPartsList;

    @Builder
    public CollaboRequestListForPostDto(Long collaboId, String title, String nickname,
                                        String profileImg, LocalDateTime createdAt, LocalDateTime modifiedAt, List<String> musicPartsList) {
        this.collaboId = collaboId;
        this.title = title;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.musicPartsList = musicPartsList;

    }
}
