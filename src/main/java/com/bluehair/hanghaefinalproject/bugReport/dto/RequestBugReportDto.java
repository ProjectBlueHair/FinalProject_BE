package com.bluehair.hanghaefinalproject.bugReport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "버그 리포트 작성 DTO")
@Getter
public class RequestBugReportDto {
    @Schema(description = "버그 리포트 내용", required = true, example = "이러이러할 때 버그가 납니다.")
    private String contents;

    public BugReportDto toBugReportDto() {
        return BugReportDto.builder()
                .contents(contents)
                .build();
    }
}
