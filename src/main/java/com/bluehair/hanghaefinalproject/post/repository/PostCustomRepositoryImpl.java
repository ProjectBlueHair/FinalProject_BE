package com.bluehair.hanghaefinalproject.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import static com.bluehair.hanghaefinalproject.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateNickname(String before, String after) {
        jpaQueryFactory
                .update(post)
                .where(post.nickname.eq(before))
                .set(post.nickname, after)
                .execute();

    }
}
