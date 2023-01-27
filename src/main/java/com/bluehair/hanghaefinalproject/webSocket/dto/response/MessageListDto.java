package com.bluehair.hanghaefinalproject.webSocket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
@Schema(description = "메세지 목록 조회 응답 Dto")
@Getter
public class MessageListDto {
    @Schema(description = "닉네임", example ="닉네임")
    private String nickname;
    @Schema(description = "프로필 이미지", example ="프로필 이미지")
    private String profileImg;
    @Schema(description = "메세지 내용", example ="메세지 내용")
    private String message;

    public MessageListDto(String message, String nickname, String profileImg) {

        this.message = message;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
