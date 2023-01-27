package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.Job;
import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;

import java.util.List;

public interface JobRepository {
    Job save(Job job);
    void deleteAllByMemberDetail(MemberDetail memberDetail);
    void saveJobList(List<Job> jobList);
}
