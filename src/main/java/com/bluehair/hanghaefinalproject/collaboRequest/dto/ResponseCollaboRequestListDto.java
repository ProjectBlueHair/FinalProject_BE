package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseCollaboRequestListDto {
    private Long collaboId;
    private String title;
    private String nickname;
    private String profileImg;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String musicFile;
    private String musicPart;

    public ResponseCollaboRequestListDto(Long collaboId, String title, String nickname,
                                         String profileImg, LocalDateTime createdAt, LocalDateTime modifiedAt,
                                         String musicPart, String musicFile) {
        this.collaboId = collaboId;
        this.title = title;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.musicPart = musicPart;
        this.musicFile = musicFile;
    }
}
