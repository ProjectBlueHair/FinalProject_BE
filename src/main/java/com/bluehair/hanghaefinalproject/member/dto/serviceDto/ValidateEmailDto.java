package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidateEmailDto {
    private String email;

    @Builder
    public ValidateEmailDto(String email) {
        this.email = email;
    }
}
