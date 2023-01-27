package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Getter;

@Getter
public class SettingMemberDto {
    private String nickname;
    private String password;
    private Boolean commentNotify;
    private Boolean likeNotify;
    private Boolean dmNotify;
    private Boolean followNotify;
}
