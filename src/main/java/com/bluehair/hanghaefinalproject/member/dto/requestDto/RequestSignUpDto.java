package com.bluehair.hanghaefinalproject.member.dto.requestDto;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SignUpDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "회원 가입 요청 Dto")
@Getter
public class RequestSignUpDto {
    @Schema(description = "회원 Email(로그인 시 사용)", required = true, example = "test01@test.com")
    private String email;
    @Schema(description = "회원 닉네임", required = true, example = "test01")
    private String nickname;
    @Schema(description = "회원 비밀번호", required = true, example = "qwe123!@#")
    private String password;
    @Schema(description = "회원 프로필 사진", example = "Profile Img from S3")
    private String profileImg;

    public SignUpDto toSignUpDto() {
        return SignUpDto.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .profileImg(profileImg)
                .build();
    }
}
