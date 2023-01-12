package com.bluehair.hanghaefinalproject.tag.repository;

import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagRepository {
    Tag save(Tag tag);
    void saveTagList(List<Tag> tagList);

    List<Tag> findAllByPostId(Long id);
}
