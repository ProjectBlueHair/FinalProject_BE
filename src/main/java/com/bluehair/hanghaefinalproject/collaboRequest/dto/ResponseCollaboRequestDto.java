package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Schema(description = "콜라보 리퀘스트 상세 조회 Dto")
@Getter
@Setter
public class ResponseCollaboRequestDto {
    @Schema(description = "콜라보 리퀘스트 게시글 Id", example = "1")
    private Long postId;
    @Schema(description = "콜라보 리퀘스트 내용", example = "콜라보하고 싶습니다.")
    private String contents;
    @Schema(description = "닉네임", example = "test01")
   private String nickname;
    @Schema(description = "작성 시간", example = "시간")
   private String createdAt;
    @Schema(description = "수정 시간", example = "시간")
   private String modifiedAt;
    @Schema(description = "삭제 여부", example = "false")
   private Boolean activated;
    @Schema(description = "승인 여부", example = "false")
    private Boolean approval;
    @Schema(description = "음악파일 리스트", example = "musicList")
    private List<ResponseMusicDto> musicList;

    @Builder
    public ResponseCollaboRequestDto(CollaboRequest collaboRequest, List<ResponseMusicDto> musicDtoList) {
        this.postId = collaboRequest.getPost().getId();
        this.contents = collaboRequest.getContents();
        this.nickname = collaboRequest.getNickname();
        this.createdAt = LocalDateTimeConverter.timeToString(collaboRequest.getCreatedAt());
        this.modifiedAt = LocalDateTimeConverter.timeToString(collaboRequest.getModifiedAt());
        this.activated = collaboRequest.getActivated();
        this.approval = collaboRequest.getApproval();
        this.musicList = musicDtoList;
    }
}
