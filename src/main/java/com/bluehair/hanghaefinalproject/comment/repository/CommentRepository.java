package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentListDto;
import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.ReplyDto;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long postId);

    void deleteById(Long commentId);

    List<Comment> findByParentsId(Long parentsId);

    List<Comment> findByPostIdAndParentsId(Long postId, Long parentsId);
}
