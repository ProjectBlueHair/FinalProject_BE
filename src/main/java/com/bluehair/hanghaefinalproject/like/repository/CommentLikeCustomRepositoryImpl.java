package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.like.entity.CommentLike;
import com.bluehair.hanghaefinalproject.like.entity.QCommentLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.bluehair.hanghaefinalproject.like.entity.QCommentLike.commentLike;

@Repository
@RequiredArgsConstructor
public class CommentLikeCustomRepositoryImpl implements CommentLikeCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;


    @Override
    public CommentLike save(CommentLike commentLike) {
        em.persist(commentLike);
        return commentLike;
    }

    @Override
    public void delete(CommentLike commentLike) {
        jpaQueryFactory
                .delete(QCommentLike.commentLike)
                .where(QCommentLike.commentLike.eq(commentLike))
                .execute();
    }

    @Override
    public void deleteByCommentId(Long commentId) {
        jpaQueryFactory
                .delete(commentLike)
                .where(commentLike.comment.id.eq(commentId))
                .execute();
    }

    @Override
    public Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long id) {
        CommentLike commentLike1 = jpaQueryFactory.select(commentLike)
                .from(commentLike)
                .where(commentLike.comment.id.eq(commentId).and(commentLike.member.id.eq(id))).fetchOne();
        return Optional.ofNullable(commentLike1);
    }

    @Override
    public void deleteAllByComment(Comment comment) {
        jpaQueryFactory
                .delete(commentLike)
                .where(commentLike.comment.eq(comment))
                .execute();

    }
}
