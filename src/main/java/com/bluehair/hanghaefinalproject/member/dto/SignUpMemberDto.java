package com.bluehair.hanghaefinalproject.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpMemberDto {
    private String email;
    private String nickname;
    private String password;

    @Builder
    public SignUpMemberDto(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
