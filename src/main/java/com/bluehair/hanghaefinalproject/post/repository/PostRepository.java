package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PostRepository {
    Post save(Post post);
    void deleteById(Long postId);

    Optional<Post> findById(Long postId);
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByTitleContainsOrContentsContains(Pageable pageable, String search, String searchContents);
    List<Post> findByContentsContains(Pageable pageable, String search);

    List<Post> findByNickname(Pageable pageable,String nickname);
}
