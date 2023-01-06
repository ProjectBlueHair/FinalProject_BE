package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CollaboRequestListForPostDto {
    private Long collaboId;
    private String title;
    private String nickname;
    private String profileImg;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
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
