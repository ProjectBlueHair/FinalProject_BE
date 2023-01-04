package com.bluehair.hanghaefinalproject.member.dto.requestDto;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.LoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
@Schema(description = "로그인 요청 Dto")
@Getter
public class RequestLoginDto {
    @Schema(description = "로그인 Email", required = true, example = "test01@test.com")
    private String email;
    @Schema(description = "로그인 Password", required = true, example = "qwe123!@#")
    private String password;

    public LoginDto toLoginMemberDto() {
        return LoginDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
