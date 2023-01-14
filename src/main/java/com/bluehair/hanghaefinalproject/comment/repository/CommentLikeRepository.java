package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.CommentLike;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
    void delete(CommentLike commentLike);
    Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long id);
}
