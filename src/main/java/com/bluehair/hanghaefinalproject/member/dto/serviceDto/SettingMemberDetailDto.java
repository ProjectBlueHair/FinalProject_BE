package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

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

    private List<String> jobList;
    @Builder
    public SettingMemberDetailDto(String aboutMe, Boolean facebookActivated, String facebookURL, Boolean instagramActivated, String instagramURL, Boolean twitterActivated, String twitterURL, Boolean linkedinActivated, String linkedinURL, List<String> jobList) {
        this.aboutMe = aboutMe;
        this.facebookActivated = facebookActivated;
        this.facebookURL = facebookURL;
        this.instagramActivated = instagramActivated;
        this.instagramURL = instagramURL;
        this.twitterActivated = twitterActivated;
        this.twitterURL = twitterURL;
        this.linkedinActivated = linkedinActivated;
        this.linkedinURL = linkedinURL;
        this.jobList = jobList;
    }
}
