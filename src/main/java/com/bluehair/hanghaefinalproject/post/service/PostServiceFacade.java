package com.bluehair.hanghaefinalproject.post.service;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.serviceDto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.collaboRequest.service.CollaboRequestService;
import com.bluehair.hanghaefinalproject.music.service.MusicService;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceFacade {
    private final PostService postService;
    private final CollaboRequestService collaboRequestService;
    private final MusicService musicService;

    @Transactional
    public Long createPost(CustomUserDetails userDetails, PostDto postDto, CollaboRequestDetailsDto collaboRequestDetailsDto,
                           List<String> musicPartList, List<MultipartFile> musicFileList) throws UnsupportedAudioFileException, IOException {
        Long postId = postService.createPost(postDto, userDetails.getMember().getNickname());
        Long collaboRequestId = collaboRequestService.collaboRequest(postId, collaboRequestDetailsDto, userDetails.getMember());
        musicService.saveMusic(musicFileList, postId, musicPartList, collaboRequestId);

        collaboRequestService.approveCollaboRequest(collaboRequestId, userDetails.getMember());
        musicService.mixMusic(collaboRequestId);
        return postId;
    }
}
