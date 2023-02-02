package com.bluehair.hanghaefinalproject.bugReport.dto;

import com.bluehair.hanghaefinalproject.bugReport.entity.BugReport;
import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "버그 리포트 응답 Dto")
@Getter
public class ResponseBugReportDto {
    @Schema(description = "생성 시간", example = "2022-12-31")
    private String createdAt;

    @Schema(description = "작성자", example = "닉네임")
    private String nickname;

    @Schema(description = "작성 내용", example = "~부분에서 ~문제가 있습니다")
    private String contents;

    @Builder
    public ResponseBugReportDto(BugReport bugReport) {
        this.createdAt = LocalDateTimeConverter.timeToString8digits(bugReport.getCreatedAt());
        this.nickname = bugReport.getNickname();
        this.contents = bugReport.getContents();
    }
}
