package com.bluehair.hanghaefinalproject.webSocket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
@Schema(description = "메세지 목록 조회 응답 Dto")
@Getter
public class ResponseMessageDto {
    @Schema(description = "닉네임", example ="닉네임")
    private String nickname;
    @Schema(description = "프로필 이미지", example ="프로필 이미지")
    private String profileImg;
    @Schema(description = "메세지 내용", example ="메세지 내용")
    private String message;
    @Schema(description = "보낸 날짜", example = "보낸 날짜")
    private String date;
    @Schema(description = "보낸 시간", example = "보낸 시간")
    private String time;

    public ResponseMessageDto(String message, String nickname, String profileImg, String date, String time) {
        this.message = message;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.date = date;
        this.time = time;
    }
}
