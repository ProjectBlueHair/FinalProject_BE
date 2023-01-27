package com.bluehair.hanghaefinalproject.member.repository.JPA;

import com.bluehair.hanghaefinalproject.member.entity.Job;
import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import com.bluehair.hanghaefinalproject.member.repository.JobRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JPAJobRepository extends JpaRepository<Job, Long>, JobRepository {
    @Transactional
    @Modifying
    @Query("DELETE from Job t where t.memberDetail = :MemberDetail")
    void deleteAllByMemberDetail(@Param("MemberDetail") MemberDetail memberDetail);
    default void saveJobList(List<Job> jobList) {
        saveAll(jobList);
    }
}
