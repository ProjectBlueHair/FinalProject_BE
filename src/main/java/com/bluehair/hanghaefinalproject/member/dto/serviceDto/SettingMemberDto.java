package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SettingMemberDto {
    private String nickname;
    private String password;
    private String profileImg;
    private Boolean commentNotify;
    private Boolean likeNotify;
    private Boolean dmNotify;
    private Boolean followNotify;

    public void encryptPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
    @Builder
    public SettingMemberDto(String nickname, String password, String profileImg, Boolean commentNotify, Boolean likeNotify, Boolean dmNotify, Boolean followNotify) {
        this.nickname = nickname;
        this.password = password;
        this.profileImg = profileImg;
        this.commentNotify = commentNotify;
        this.likeNotify = likeNotify;
        this.dmNotify = dmNotify;
        this.followNotify = followNotify;
    }
}
