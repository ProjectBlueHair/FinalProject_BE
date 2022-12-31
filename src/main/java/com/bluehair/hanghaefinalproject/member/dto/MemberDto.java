package com.bluehair.hanghaefinalproject.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
    private String email;
    private String nickname;
    private String password;

    @Builder
    public MemberDto(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
