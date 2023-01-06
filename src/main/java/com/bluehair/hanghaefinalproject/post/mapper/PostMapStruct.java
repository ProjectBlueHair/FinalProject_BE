package com.bluehair.hanghaefinalproject.post.mapper;

import com.bluehair.hanghaefinalproject.post.dto.serviceDto.MainPostDto;
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
    MainPostDto PostToMainPostDto(Long id, String title, Long likeCount, Long viewCount, List<String> musicFileList
                                ,List<MainProfileDto> mainProfileDtos);
}
