package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JPAPostRepository extends JpaRepository<Post, Long> {
    Post save(Post post);
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByTitleContainsOrContentsContains(Pageable pageable, String search, String searchContents);
    List<Post> findByContentsContains(Pageable pageable, String search);
    List<Post> findByNickname(Pageable pageable,String nickname);
    Optional<Post> findById(Long postId);
    void deleteById(Long postId);

}
