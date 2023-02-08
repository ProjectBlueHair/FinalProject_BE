package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.repository.query.Param;

public interface CommentCustomRepository {
    void deleteAllByPost(@Param("Post") Post post);
    void updateNickname(String before, String after);
}
