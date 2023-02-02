package com.bluehair.hanghaefinalproject.bugReport.service;

import com.bluehair.hanghaefinalproject.bugReport.dto.BugReportDto;
import com.bluehair.hanghaefinalproject.bugReport.dto.ResponseBugReportDto;
import com.bluehair.hanghaefinalproject.bugReport.entity.BugReport;
import com.bluehair.hanghaefinalproject.bugReport.repository.BugReportRepository;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BugReportService {
    private final BugReportRepository bugReportRepository;

    @Transactional
    public void postBugReport(@AuthenticationPrincipal CustomUserDetails userDetails, BugReportDto bugReportDto) {
        String nickname = null;
        if (userDetails != null) {
            nickname = userDetails.getMember().getNickname();
        }
        BugReport bugReport = BugReport.builder()
                .contents(bugReportDto.getContents())
                .nickname(nickname)
                .build();

        bugReportRepository.save(bugReport);
    }

    @Transactional(readOnly = true)
    public List<ResponseBugReportDto> getBugReportList() {
        List<BugReport> bugReportList = bugReportRepository.findAllByOrderByCreatedAtDesc();
        List<ResponseBugReportDto> responseBugReportDtoList = new ArrayList<>();

        for (BugReport bugReport : bugReportList) {
            responseBugReportDtoList.add(ResponseBugReportDto.builder().bugReport(bugReport).build());
        }

        return responseBugReportDtoList;
    }
}
