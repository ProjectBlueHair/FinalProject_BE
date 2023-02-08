package com.bluehair.hanghaefinalproject.member.repository.CustomImpl;

import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import com.bluehair.hanghaefinalproject.member.repository.Custom.JobCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.bluehair.hanghaefinalproject.member.entity.QJob.job;

@Repository
@RequiredArgsConstructor
public class JobCustomRepositoryImpl implements JobCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    @Override
    public void deleteAllByMemberDetail(MemberDetail memberDetail) {
        jpaQueryFactory
                .delete(job)
                .where(job.memberDetail.eq(memberDetail))
                .execute();
    }
}
