package com.bluehair.hanghaefinalproject.member.repository.JPA;

import com.bluehair.hanghaefinalproject.member.entity.Job;
import com.bluehair.hanghaefinalproject.member.repository.JobRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAJobRepository extends JpaRepository<Job, Long>, JobRepository {
}
