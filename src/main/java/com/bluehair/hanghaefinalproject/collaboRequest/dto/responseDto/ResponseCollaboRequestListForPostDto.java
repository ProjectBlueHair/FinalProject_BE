package com.bluehair.hanghaefinalproject.collaboRequest.dto.responseDto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "콜라보 리퀘스트 전체 조회 Dto")
@Getter
public class ResponseCollaboRequestListForPostDto {
    private Long collaboId;
    @Schema(description = "닉네임", example = "test01")
    private String nickname;

    private String contents;

    private Boolean isFollowed;

    private Long followerCount;
    @Schema(description = "프로필 이미지", example = "Profile Img from S3")
    private String profileImg;
    @Schema(description = "작성시간", example = "작성시간")
    private String createdAt;
    @Schema(description = "작성시간", example = "수정시간")
    private String modifiedAt;
    @ArraySchema
    private List<String> musicPartsList;

    @Builder
    public ResponseCollaboRequestListForPostDto(Long collaboId, String nickname, String contents,
                                                String profileImg, Long followerCount, Boolean isFollowed, LocalDateTime createdAt,
                                                LocalDateTime modifiedAt, List<String> musicPartsList) {
        this.collaboId = collaboId;
        this.nickname = nickname;
        this.contents = contents;
        this.profileImg = profileImg;
        this.followerCount = followerCount;
        this.isFollowed = isFollowed;
        this.createdAt = LocalDateTimeConverter.timeToString(createdAt);
        this.modifiedAt = LocalDateTimeConverter.timeToString(modifiedAt);
        this.musicPartsList = musicPartsList;

    }
}
