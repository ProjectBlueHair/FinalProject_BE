package com.bluehair.hanghaefinalproject.comment.dto.serviceDto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyDto {

    private Long id;
    private String contents;
    private String nickname;
    private String profileImg;
    private Long parentsId;
    private String createdAt;
    private String modifiedAt;

    @Builder
    public ReplyDto(Long id, String contents, String nickname, String profileImg, Long parentsId, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.contents = contents;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.parentsId = parentsId;
        this.createdAt = LocalDateTimeConverter.timeToString(createdAt);
        this.modifiedAt = LocalDateTimeConverter.timeToString(modifiedAt);
    }

}
