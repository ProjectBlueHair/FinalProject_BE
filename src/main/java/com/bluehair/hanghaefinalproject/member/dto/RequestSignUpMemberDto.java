package com.bluehair.hanghaefinalproject.member.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
public class RequestSignUpMemberDto {
    @ApiParam(value = "회원 Email(로그인 시 사용)", required = true, example = "test01@test.com")
    private String email;
    @ApiParam(value = "회원 닉네임", required = true, example = "test01")
    private String nickname;
    @ApiParam(value = "회원 비밀번호", required = true, example = "qwe123!@#")
    private String password;

    public SignUpMemberDto toSignUpMemberDto() {
        return SignUpMemberDto.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }
}
