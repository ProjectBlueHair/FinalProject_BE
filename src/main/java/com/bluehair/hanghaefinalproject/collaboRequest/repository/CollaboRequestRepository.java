package com.bluehair.hanghaefinalproject.collaboRequest.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollaboRequestRepository extends JpaRepository<CollaboRequest, Long> {

    List<CollaboRequest> findAllByPostId(Long id);

    List<CollaboRequest> findAllByPost(Post post);

    @Transactional
    @Modifying
    @Query("DELETE from CollaboRequest c where c.post = :Post")
    void deleteAllByPost(@Param("Post") Post post);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE CollaboRequest c set c.nickname = :after where c.nickname = :before")
    void updateNickname(@Param("before") String before, @Param("after") String after);
}
