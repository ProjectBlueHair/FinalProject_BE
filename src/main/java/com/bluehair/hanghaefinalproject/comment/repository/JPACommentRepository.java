package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JPACommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);

    Optional<Comment> findById(Long postId);

    void deleteById(Long commentId);

    List<Comment> findByParentsId(Long parentsId);

    List<Comment> findByPostId(Long postId);

    List<Comment> findByPostIdAndParentsId(Long postId, Long parentsId);
}
