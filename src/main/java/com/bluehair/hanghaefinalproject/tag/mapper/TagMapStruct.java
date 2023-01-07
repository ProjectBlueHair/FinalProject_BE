package com.bluehair.hanghaefinalproject.tag.mapper;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapStruct {
    TagMapStruct TAG_MAPPER = Mappers.getMapper(TagMapStruct.class);

    default Tag stringToTag(String contents, Post post) {
        return Tag.builder()
                .contents(contents)
                .post(post)
                .build();
    }
}
