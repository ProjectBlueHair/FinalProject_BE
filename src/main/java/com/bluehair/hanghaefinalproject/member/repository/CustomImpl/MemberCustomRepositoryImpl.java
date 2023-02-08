package com.bluehair.hanghaefinalproject.member.repository.CustomImpl;

import com.bluehair.hanghaefinalproject.member.repository.Custom.MemberCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.bluehair.hanghaefinalproject.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    @Override
    public void updateFollowerCount(Long followerCount, Long memberId) {
        jpaQueryFactory
                .update(member)
                .where(member.id.eq(memberId))
                .set(member.followerCount, followerCount)
                .execute();
    }

    @Transactional
    @Override
    public void updateFollowingCount(Long followingCount, Long memberId) {
        jpaQueryFactory
                .update(member)
                .where(member.id.eq(memberId))
                .set(member.followingCount, followingCount)
                .execute();
    }
}
