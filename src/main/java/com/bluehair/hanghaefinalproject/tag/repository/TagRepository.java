package com.bluehair.hanghaefinalproject.tag.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface TagRepository {
    Tag save(Tag tag);
    void saveTagList(List<Tag> tagList);
    List<Tag> findAllByPostId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE from Tag t where t.post = :Post")
    void deleteAllByPost(@Param("Post") Post post);
}
