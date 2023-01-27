package com.bluehair.hanghaefinalproject.member.entity;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SettingMemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String profileImg;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role = MemberRole.SILVER;

    @Column
    private Long followerCount = 0L;

    @Column
    private Long followingCount = 0L;

    @Column
    private Social social;
    @Column
    private String refreshToken;

    @Column
    private Boolean commentNotify = true;
    @Column
    private Boolean likeNotify = true;
    @Column
    private Boolean dmNotify = true;
    @Column
    private Boolean followNotify = true;

    @OneToOne
    @JoinColumn(name = "member_detail_id")
    private MemberDetail memberDetail;

    @Builder
    public Member(String email, String nickname, String password, String profileImg) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImg = profileImg;
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void updateSetting(SettingMemberDto settingMemberDto) {
        if (settingMemberDto.getNickname()!=null){
            this.nickname = settingMemberDto.getNickname();
        }
        if (settingMemberDto.getPassword()!=null){
            this.password = settingMemberDto.getPassword();
        }
        if(settingMemberDto.getProfileImg()!=null){
            this.profileImg = settingMemberDto.getProfileImg();
        }
        if(settingMemberDto.getCommentNotify()!=null){
            this.commentNotify = settingMemberDto.getCommentNotify();
        }
        if(settingMemberDto.getLikeNotify()!=null){
            this.likeNotify = settingMemberDto.getLikeNotify();
        }
        if(settingMemberDto.getDmNotify()!=null){
            this.dmNotify = settingMemberDto.getDmNotify();
        }
        if (settingMemberDto.getFollowNotify()!=null){
            this.followNotify = settingMemberDto.getFollowNotify();
        }
    }
    public void updateMemberDetail(MemberDetail memberDetail){
        this.memberDetail = memberDetail;
    }
}
