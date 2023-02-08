package com.bluehair.hanghaefinalproject.tag.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;

import org.springframework.data.repository.query.Param;

public interface TagCustomRepository {
    void deleteAllByPost(@Param("Post") Post post);
}
