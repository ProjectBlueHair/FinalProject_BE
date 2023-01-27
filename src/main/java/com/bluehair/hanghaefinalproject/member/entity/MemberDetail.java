package com.bluehair.hanghaefinalproject.member.entity;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SettingMemberDetailDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class MemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String aboutMe;

    @Column
    private Boolean facebookActivated;
    @Column
    private String facebookURL;

    @Column
    private Boolean instagramActivated;
    @Column
    private String instagramURL;

    @Column
    private Boolean twitterActivated;
    @Column
    private String twitterURL;

    @Column
    private Boolean linkedinActivated;
    @Column
    private String linkedinURL;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberDetail")
    private List<Job> jobList = new ArrayList<>();

    public void updateSettings(SettingMemberDetailDto dto) {
        this.aboutMe = dto.getAboutMe();
        this.facebookActivated = dto.getFacebookActivated();
        this.facebookURL = dto.getFacebookURL();
        this.instagramActivated = dto.getInstagramActivated();
        this.instagramURL = dto.getInstagramURL();
        this.twitterActivated = dto.getTwitterActivated();
        this.twitterURL = dto.getTwitterURL();
        this.linkedinActivated = dto.getLinkedinActivated();
        this.linkedinURL = dto.getLinkedinURL();
    }
}
