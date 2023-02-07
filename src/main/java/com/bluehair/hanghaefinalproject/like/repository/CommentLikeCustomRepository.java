package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.like.entity.CommentLike;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentLikeCustomRepository {

    CommentLike save(CommentLike commentLike);
    void delete(CommentLike commentLike);
    void deleteByCommentId(Long commentId);
    Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long id);

    @Transactional
    @Modifying
    @Query("DELETE from CommentLike c where c.comment = :Comment")
    void deleteAllByComment(@Param("Comment") Comment comment);
}
