package com.bluehair.hanghaefinalproject.post.service;

import com.bluehair.hanghaefinalproject.post.dto.serviceDto.InfoPostDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.exception.NotFoundPostRequestException;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.post.mapper.PostMapStruct.POST_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    @Transactional
    public void createPost(PostDto postDto, String nickname) {

        Post post = POST_MAPPER.PostDtoToPost(postDto, nickname);
        postRepository.save(post);

    }

    @Transactional(readOnly = true)
    public InfoPostDto infoPost(Long postid) {

        Post post = postRepository.findById(postid).orElseThrow(
                () -> new NotFoundPostRequestException(POST_NOT_FOUND)
        );

        return new InfoPostDto(post);
    }
}
