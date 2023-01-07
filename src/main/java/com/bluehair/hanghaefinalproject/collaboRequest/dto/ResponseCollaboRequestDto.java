package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Schema(description = "콜라보 리퀘스트 상세 조회 Dto")
@Getter
@Setter
public class ResponseCollaboRequestDto {
    @Schema(description = "콜라보 리퀘스트 제목", example = "콜라보 요청합니다.")
    private String title;
    @Schema(description = "콜라보 리퀘스트 내용", example = "콜라보하고 싶습니다.")
    private String contents;
    @Schema(description = "닉네임", example = "test01")
   private String nickname;
    @Schema(description = "작성 시간", example = "시간")
   private LocalDateTime createdAt;
    @Schema(description = "수정 시간", example = "시간")
   private LocalDateTime modifiedAt;
    @Schema(description = "삭제 여부", example = "false")
   private Boolean activated;
    @Schema(description = "음악파일 리스트", example = "musicList")
   private List<ResponseMusicDto> musicList;

    @Builder
    public ResponseCollaboRequestDto(CollaboRequest collaboRequest, List<ResponseMusicDto> musicDtoList) {
        this.title = collaboRequest.getTitle();
        this.contents = collaboRequest.getContents();
        this.nickname = collaboRequest.getNickname();
        this.createdAt = collaboRequest.getCreatedAt();
        this.modifiedAt = collaboRequest.getModifiedAt();
        this.activated = collaboRequest.getActivated();
        this.musicList = musicDtoList;
    }
}
