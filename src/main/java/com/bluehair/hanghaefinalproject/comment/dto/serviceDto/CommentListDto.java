package com.bluehair.hanghaefinalproject.comment.dto.serviceDto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "댓글 리스트 조회 Dto")
@Getter
public class CommentListDto {

    @Schema(description = "댓글 번호", example = "댓글 번호")
    private Long id;
    @Schema(description = "유저 프로필 이미지", example = "유저 프로필 이미지")
    private String profileImg;
    @Schema(description = "유저 닉네임", example = "유저 닉네임")
    private String nickname;
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String contents;
    @Schema(description = "댓글 부모 번호", example = "댓글 부모 번호")
    private Long parentsId;
    @Schema(description = "작성시간", example = "작성시간")
    private String createdAt;
    @Schema(description = "수정시간", example = "수정시간")
    private String modifiedAt;

    @Builder
    public CommentListDto(Long id,String profileImg, String nickname,String contents, Long parentsId
                          ,LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.contents = contents;
        this.parentsId = parentsId;
        this.createdAt = LocalDateTimeConverter.timeToString(createdAt);
        this.modifiedAt = LocalDateTimeConverter.timeToString(modifiedAt);
    }
}
