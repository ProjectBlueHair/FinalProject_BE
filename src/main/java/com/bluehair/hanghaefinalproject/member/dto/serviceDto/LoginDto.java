package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private String email;
    private String password;

    @Builder
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
