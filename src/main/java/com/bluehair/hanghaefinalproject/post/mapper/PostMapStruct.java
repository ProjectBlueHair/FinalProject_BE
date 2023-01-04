package com.bluehair.hanghaefinalproject.post.mapper;

import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import com.bluehair.hanghaefinalproject.post.entity.Post;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapStruct {
    PostMapStruct POST_MAPPER = Mappers.getMapper(PostMapStruct.class);
    Post PostDtoToPost(PostDto postDto, String nickname);

}
