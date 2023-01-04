package com.bluehair.hanghaefinalproject.post.mapper;

import com.bluehair.hanghaefinalproject.post.dto.RequestPostDto;
import com.bluehair.hanghaefinalproject.post.entity.Post;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapStruct {
    PostMapStruct POST_MAPPER = Mappers.getMapper(PostMapStruct.class);
    Post RequestPostDtoToPost(RequestPostDto requestPostDto, String nickname);

}
