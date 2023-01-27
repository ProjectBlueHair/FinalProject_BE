package com.bluehair.hanghaefinalproject.webSocket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "채팅방 목록 조회 Dto")
@Getter
public class RoomListDto {

    @Schema(description = "채팅방 번호", example = "채팅방 번호")
    private Long roomId;
    @Schema(description = "닉네임", example = "닉네임")
    private String nickname;
    @Schema(description = "프로필 이미지", example = "프로필 이미지")
    private String profileImg;

    @Builder
    public RoomListDto(Long roomId,String nickname, String profileImg){
        this.roomId = roomId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
