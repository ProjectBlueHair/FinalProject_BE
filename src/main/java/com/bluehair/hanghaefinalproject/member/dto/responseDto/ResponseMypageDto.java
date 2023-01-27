package com.bluehair.hanghaefinalproject.member.dto.responseDto;

import com.bluehair.hanghaefinalproject.member.entity.Job;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "마이페이지 응답 Dto")
@Getter
public class ResponseMypageDto {
    @Schema(description = "본인의 페이지일 경우 true", example = "true")
    private Boolean isMine;

    @Schema(description = "프로필 이미지", example = "img from S3")
    private String profileImg;
    @Schema(description = "닉네임", example = "modified nickname")
    private String nickname;
    @Schema(description = "직업 리스트", example = "[\"Sound Engineer\", \"Producer\"]")
    private List<String> jobList;
    @Schema(description = "회원 Email", example = "asdf@asdf.com")
    private String email;

    @Schema(description = "FaceBook Link Activation", example = "true")
    private Boolean facebookActivated;
    @Schema(description = "FaceBook Link", example = "www.facebook.com/adfasdf")
    private String facebookURL;
    @Schema(description = "Instagram Link Activation", example = "true")
    private Boolean instagramActivated;
    @Schema(description = "Instagram Link", example = "www.instagram.com/adfasdf")
    private String instagramURL;
    @Schema(description = "Twitter Link Activation", example = "true")
    private Boolean twitterActivated;
    @Schema(description = "Twitter Link", example = "www.twitter.com/adfasdf")
    private String twitterURL;
    @Schema(description = "LinkedIn Link Activation", example = "true")
    private Boolean linkedinActivated;
    @Schema(description = "LinkedIn Link", example = "www.linkedin.com/adfasdf")
    private String linkedinURL;

    @Schema(description = "이 사람을 팔로우하는 사람", example = "2300000")
    private Long followerCount;
    @Schema(description = "이 사람이 팔로우하는 사람", example = "1235")
    private Long followingCount;

    @Schema(description = "AboutMe", example = "느그 서장 남천동 살제")
    private String aboutMe;

    @Builder
    public ResponseMypageDto(Member member, MemberDetail memberDetail, Boolean isMine) {
        this.isMine = isMine;
        this.profileImg = member.getProfileImg();
        this.nickname = member.getNickname();

        List<String> jobListAsString = new ArrayList<>();
        for (Job job : memberDetail.getJobList()) {
            jobListAsString.add(job.getName());
        }
        this.jobList = jobListAsString;

        this.email = member.getEmail();
        this.facebookActivated = memberDetail.getFacebookActivated();
        this.facebookURL = memberDetail.getFacebookURL();
        this.instagramActivated = memberDetail.getInstagramActivated();
        this.instagramURL = memberDetail.getInstagramURL();
        this.twitterActivated = memberDetail.getTwitterActivated();
        this.twitterURL = memberDetail.getTwitterURL();
        this.linkedinActivated = memberDetail.getLinkedinActivated();
        this.linkedinURL = memberDetail.getLinkedinURL();

        this.followerCount = member.getFollowerCount();
        this.followingCount = member.getFollowingCount();

        this.aboutMe = memberDetail.getAboutMe();
    }
}
