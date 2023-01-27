package com.bluehair.hanghaefinalproject.member.entity;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SettingMemberDetailDto;
import lombok.Builder;
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

    @OneToOne(mappedBy = "memberDetail")
    private Member member;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "memberDetail")
    private List<Job> jobList = new ArrayList<>();

    @Builder
    public MemberDetail(Member member) {
        this.member = member;
    }

    public void updateSettings(SettingMemberDetailDto dto) {
        if(dto.getAboutMe()!=null){
            this.aboutMe = dto.getAboutMe();
        }
        if(dto.getFacebookActivated()!=null){
            this.facebookActivated = dto.getFacebookActivated();
        }
        if(dto.getFacebookURL()!=null){
            this.facebookURL = dto.getFacebookURL();
        }
        if(dto.getInstagramActivated()!=null){
            this.instagramActivated = dto.getInstagramActivated();
        }
        if(dto.getInstagramURL()!=null){
            this.instagramURL = dto.getInstagramURL();
        }
        if(dto.getTwitterActivated()!=null){
            this.twitterActivated = dto.getTwitterActivated();
        }
        if(dto.getTwitterURL()!=null){
            this.twitterURL = dto.getTwitterURL();
        }
        if(dto.getLinkedinActivated()!=null){
            this.linkedinActivated = dto.getLinkedinActivated();
        }
        if(dto.getLinkedinURL()!=null){
            this.linkedinURL = dto.getLinkedinURL();
        }
    }
}
