package com.bluehair.hanghaefinalproject.bugReport.controller;

import com.bluehair.hanghaefinalproject.bugReport.dto.RequestBugReportDto;
import com.bluehair.hanghaefinalproject.bugReport.dto.ResponseBugReportDto;
import com.bluehair.hanghaefinalproject.bugReport.service.BugReportService;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.POST_BUG_REPORT;

@Tag(name = "BugReport", description = "버그 리포트 관련 API")
@RestController
@RequestMapping("/api/bugreport")
@RequiredArgsConstructor
@Slf4j
public class BugReportController {
    private final BugReportService bugReportService;

    @Tag(name = "BugReport")
    @Operation(summary = "버그 리포트 작성", description = "버그 리포트 작성")
    @PostMapping
    public ResponseEntity<SuccessResponse<Object>> postBugReport(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @RequestBody RequestBugReportDto requestBugReportDto) {
        bugReportService.postBugReport(userDetails, requestBugReportDto.toBugReportDto());
        return SuccessResponse.toResponseEntity(POST_BUG_REPORT, null);
    }

    @Tag(name = "BugReport")
    @Operation(summary = "버그 리포트 조회", description = "버그 리포트 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse<List<ResponseBugReportDto>>> getBugReport() {
        return SuccessResponse.toResponseEntity(POST_BUG_REPORT, bugReportService.getBugReportList());
    }
}
