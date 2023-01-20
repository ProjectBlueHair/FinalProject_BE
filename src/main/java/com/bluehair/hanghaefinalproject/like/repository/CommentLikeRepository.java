package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.CommentLike;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
    void delete(CommentLike commentLike);
    void deleteByCommentId(Long commentId);
    Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long id);
}
