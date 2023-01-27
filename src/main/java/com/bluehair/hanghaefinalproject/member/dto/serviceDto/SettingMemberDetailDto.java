package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Getter;

@Getter
public class SettingMemberDetailDto {
    private String aboutMe;

    private Boolean facebookActivated;
    private String facebookURL;

    private Boolean instagramActivated;
    private String instagramURL;

    private Boolean twitterActivated;
    private String twitterURL;

    private Boolean linkedinActivated;
    private String linkedinURL;

}
