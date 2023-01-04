package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public interface PostRepository {
    Post save(Post post);
}
