package com.bluehair.hanghaefinalproject.collaboRequest.repository;


import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.repository.query.Param;

public interface CollaboRequestCustomRepository {


    void deleteAllByPost(@Param("Post") Post post);

    void updateNickname(@Param("before") String before, @Param("after") String after);
}
