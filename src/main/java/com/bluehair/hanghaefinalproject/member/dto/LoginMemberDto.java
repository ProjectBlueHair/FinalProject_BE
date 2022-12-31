package com.bluehair.hanghaefinalproject.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMemberDto {
    private String email;
    private String password;

    @Builder
    public LoginMemberDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
