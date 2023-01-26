package com.bluehair.hanghaefinalproject.post.mapper;

import com.bluehair.hanghaefinalproject.post.dto.responseDto.ResponseInfoPostDto;
import com.bluehair.hanghaefinalproject.post.dto.responseDto.ResponseMainPostDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.MainProfileDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import com.bluehair.hanghaefinalproject.post.entity.Post;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapStruct {
    PostMapStruct POST_MAPPER = Mappers.getMapper(PostMapStruct.class);
    Post PostDtoToPost(PostDto postDto, String nickname);
    ResponseMainPostDto PostToMainPostDto(Long id, String title, String postImg , Long likeCount, Long viewCount, String musicFile
                                , List<String> tagList, List<MainProfileDto> mainProfileList, boolean isLiked);

    ResponseInfoPostDto postToResponseInfoPostDto(Post post, Boolean isLiked);
}
