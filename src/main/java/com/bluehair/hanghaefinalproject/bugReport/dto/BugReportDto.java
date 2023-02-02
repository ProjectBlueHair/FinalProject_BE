package com.bluehair.hanghaefinalproject.bugReport.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BugReportDto {
    private String contents;

    @Builder
    public BugReportDto(String contents) {
        this.contents = contents;
    }
}
