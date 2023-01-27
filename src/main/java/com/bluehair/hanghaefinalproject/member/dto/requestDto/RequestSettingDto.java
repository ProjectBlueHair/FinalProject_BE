package com.bluehair.hanghaefinalproject.member.dto.requestDto;

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
}
