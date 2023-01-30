package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoLoginDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImg;
}
