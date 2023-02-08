package com.bluehair.hanghaefinalproject.collaboRequest.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bluehair.hanghaefinalproject.collaboRequest.entity.QCollaboRequest.collaboRequest;

@Repository
@RequiredArgsConstructor
public class CollaboRequestCustomRepositoryImpl implements CollaboRequestCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CollaboRequest> findAllByPostId(Long id) {
        return jpaQueryFactory.selectFrom(collaboRequest)
                .where(collaboRequest.id.eq(id))
                .fetch();
    }

    @Override
    public List<CollaboRequest> findAllByPost(Post post) {
        return jpaQueryFactory.selectFrom(collaboRequest)
                .where(collaboRequest.post.eq(post))
                .fetch();
    }

    @Override
    public void deleteAllByPost(Post post) {
        jpaQueryFactory.delete(collaboRequest)
                .where(collaboRequest.post.eq(post))
                .execute();

    }

    @Override
    public void updateNickname(String before, String after) {
        jpaQueryFactory
                .update(collaboRequest)
                .where(collaboRequest.nickname.eq(before))
                .set(collaboRequest.nickname, after)
                .execute();
    }
}
