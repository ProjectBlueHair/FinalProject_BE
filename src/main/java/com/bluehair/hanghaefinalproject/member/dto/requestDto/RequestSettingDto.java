package com.bluehair.hanghaefinalproject.member.dto.requestDto;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SettingMemberDetailDto;
import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SettingMemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "계정 및 알림 설정 요청 Dto")
@Getter
public class RequestSettingDto {
    @Schema(description = "닉네임 변경", example = "modified nickname")
    private String nickname;
    @Schema(description = "패스워드 변경", example = "modified password")
    private String password;

    @Schema(description = "프로필 이미지 변경", example = "img from S3")
    private String profileImg;

    @Schema(description = "Job List", example = "[\"Sound Engineer\", \"Producer\"]")
    private List<String> jobList;

    @Schema(description = "AboutMe", example = "느그 서장 남천동 살제")
    private String aboutMe;

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

    @Schema(description = "댓글 알림 설정", example = "true")
    private Boolean commentNotify;
    @Schema(description = "좋아요 알림 설정", example = "true")
    private Boolean likeNotify;
    @Schema(description = "DM 알림 설정", example = "true")
    private Boolean dmNotify;
    @Schema(description = "팔로우 알림 설정", example = "true")
    private Boolean followNotify;

    public SettingMemberDto requestSettingDtoToSettingMemberDto() {
        return SettingMemberDto.builder()
                .nickname(nickname)
                .password(password)
                .profileImg(profileImg)
                .commentNotify(commentNotify)
                .dmNotify(dmNotify)
                .followNotify(followNotify)
                .likeNotify(likeNotify)
                .build();
    }

    public SettingMemberDetailDto requestSettingDtoToSettingMemberDetailDto() {
        return SettingMemberDetailDto.builder()
                .aboutMe(aboutMe)
                .jobList(jobList)
                .facebookActivated(facebookActivated)
                .facebookURL(facebookURL)
                .instagramActivated(instagramActivated)
                .instagramURL(instagramURL)
                .linkedinActivated(linkedinActivated)
                .linkedinURL(linkedinURL)
                .twitterActivated(twitterActivated)
                .twitterURL(twitterURL)
                .build();
    }
}
