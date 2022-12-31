package com.bluehair.hanghaefinalproject.member.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
public class RequestLoginMemberDto {
    @ApiParam(value = "로그인 Email", required = true, example = "test01@test.com")
    private String email;
    @ApiParam(value = "로그인 Password", required = true, example = "qwe123!@#")
    private String password;

    public LoginMemberDto toLoginMemberDto() {
        return LoginMemberDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
