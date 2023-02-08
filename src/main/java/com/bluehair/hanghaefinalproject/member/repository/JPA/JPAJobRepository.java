package com.bluehair.hanghaefinalproject.member.repository.JPA;

import com.bluehair.hanghaefinalproject.member.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAJobRepository extends JpaRepository<Job, Long> {
    Job save(Job job);
    void saveJobList(List<Job> jobList);
}
