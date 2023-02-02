package com.bluehair.hanghaefinalproject.bugReport.repository;

import com.bluehair.hanghaefinalproject.bugReport.entity.BugReport;

import java.util.List;

public interface BugReportRepository {
    BugReport save(BugReport bugReport);
    List<BugReport> findAllByOrderByCreatedAtDesc();
}
