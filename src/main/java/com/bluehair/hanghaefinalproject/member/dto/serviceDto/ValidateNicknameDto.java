package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidateNicknameDto {
    private String nickname;

    @Builder
    public ValidateNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
