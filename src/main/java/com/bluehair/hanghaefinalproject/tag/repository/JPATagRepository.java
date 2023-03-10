package com.bluehair.hanghaefinalproject.tag.repository;

import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPATagRepository extends JpaRepository<Tag, Long> {
    default void saveTagList(List<Tag> tagList){
        saveAll(tagList);
    }
    Tag save(Tag tag);
    List<Tag> findAllByPostId(Long id);
}
