package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "콜라보 리퀘스트 전체 조회 Dto")
@Getter
public class CollaboRequestListForPostDto {
    private Long collaboId;
    @Schema(description = "닉네임", example = "test01")
    private String nickname;
    @Schema(description = "프로필 이미지", example = "Profile Img from S3")
    private String profileImg;
    @Schema(description = "작성시간", example = "작성시간")
    private String createdAt;
    @Schema(description = "작성시간", example = "수정시간")
    private String modifiedAt;
    @ArraySchema
    private List<String> musicPartsList;

    @Builder
    public CollaboRequestListForPostDto(Long collaboId, String nickname,
                                        String profileImg, LocalDateTime createdAt,
                                        LocalDateTime modifiedAt, List<String> musicPartsList) {
        this.collaboId = collaboId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.createdAt = LocalDateTimeConverter.timeToString(createdAt);
        this.modifiedAt = LocalDateTimeConverter.timeToString(modifiedAt);
        this.musicPartsList = musicPartsList;

    }
}
