package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.bluehair.hanghaefinalproject.comment.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Transactional
    @Override
    public void deleteAllByPost(Post post) {
        jpaQueryFactory
                .delete(comment)
                .where(comment.post.eq(post))
                .execute();
    }
    @Transactional
    @Override
    public void updateNickname(String before, String after) {
        jpaQueryFactory
                .update(comment)
                .where(comment.nickname.eq(before))
                .set(comment.nickname, after)
                .execute();
    }
}
