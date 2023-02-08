package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;

import com.bluehair.hanghaefinalproject.comment.entity.QComment;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.bluehair.hanghaefinalproject.comment.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long postId) {
        Comment comment1 = jpaQueryFactory.select(comment)
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetchOne();
        return Optional.ofNullable(comment1);
    }

    @Override
    public void deleteById(Long commentId) {
        jpaQueryFactory
                .delete(comment)
                .where(comment.id.eq(commentId))
                .execute();

    }

    @Override
    public List<Comment> findByParentsId(Long parentsId) {
        return jpaQueryFactory
                .select(comment)
                .where(comment.parentsId.eq(parentsId))
                .fetch();
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return jpaQueryFactory
                .select(comment)
                .where(comment.post.id.eq(postId))
                .fetch();
    }

    @Override
    public List<Comment> findByPostIdAndParentsId(Long postId, Long parentsId) {
        return jpaQueryFactory
                .select(comment)
                .where(comment.post.id.eq(postId).and(comment.parentsId.eq(parentsId)))
                .fetch();
    }

    @Override
    public void deleteAllByPost(Post post) {
        jpaQueryFactory
                .delete(comment)
                .where(comment.post.eq(post))
                .execute();
    }

    @Override
    public void updateNickname(String before, String after) {
        jpaQueryFactory
                .update(comment)
                .where(comment.nickname.eq(before))
                .set(comment.nickname, after)
                .execute();
    }
}
