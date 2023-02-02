package com.bluehair.hanghaefinalproject.bugReport.repository;

import com.bluehair.hanghaefinalproject.bugReport.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPABugReportRepository extends JpaRepository<BugReport, Long>, BugReportRepository {

}
