package com.bluehair.hanghaefinalproject.member.dto.responseDto;

import com.bluehair.hanghaefinalproject.member.entity.Job;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "계정 설정 응답 Dto")
@Getter
public class ResponseSettingDto {
    @Schema(description = "프로필 이미지", example = "Img from S3")
    private String profileImg;
    @Schema(description = "현재 닉네임", example = "Nickname")
    private String nickname;
    @Schema(description = "Job List", example = "[\"Sound Engineer\", \"Producer\"]")
    private List<String> jobList;

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

    @Schema(description = "AboutMe", example = "느그 서장 남천동 살제")
    private String aboutMe;

    @Schema(description = "댓글 알림 설정", example = "true")
    private Boolean commentNotify;
    @Schema(description = "좋아요 알림 설정", example = "true")
    private Boolean likeNotify;
    @Schema(description = "DM 알림 설정", example = "true")
    private Boolean dmNotify;
    @Schema(description = "팔로우 알림 설정", example = "true")
    private Boolean followNotify;

    @Builder
    public ResponseSettingDto(Member member, MemberDetail memberDetail) {
        this.profileImg = member.getProfileImg();
        this.nickname = member.getNickname();
        List<String> jobListAsString = new ArrayList<>();
        for (Job job : memberDetail.getJobList()) {
            jobListAsString.add(job.getName());
        }
        this.jobList = jobListAsString;
        this.facebookActivated = memberDetail.getFacebookActivated();
        this.facebookURL = memberDetail.getFacebookURL();
        this.instagramActivated = memberDetail.getInstagramActivated();
        this.instagramURL = memberDetail.getInstagramURL();
        this.twitterActivated = memberDetail.getTwitterActivated();
        this.twitterURL = memberDetail.getTwitterURL();
        this.linkedinActivated = memberDetail.getLinkedinActivated();
        this.linkedinURL = memberDetail.getLinkedinURL();
        this.aboutMe = memberDetail.getAboutMe();
        this.commentNotify = member.getCommentNotify();
        this.likeNotify = member.getLikeNotify();
        this.dmNotify = member.getDmNotify();
        this.followNotify = member.getFollowNotify();
    }
}
