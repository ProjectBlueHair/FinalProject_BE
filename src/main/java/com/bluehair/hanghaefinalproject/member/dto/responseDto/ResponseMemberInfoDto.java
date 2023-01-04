package com.bluehair.hanghaefinalproject.member.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "유저 정보 응답 Dto")
@Getter
public class ResponseMemberInfoDto {
    @Schema(description = "이메일", example = "test01@test.com")
    private String email;
    @Schema(description = "닉네임", example = "test01")
    private String nickname;
    @Schema(description = "프로필 사진", example = "profileImg")
    private String profileImg;

    @Builder
    public ResponseMemberInfoDto(String email, String nickname, String profileImg) {
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
