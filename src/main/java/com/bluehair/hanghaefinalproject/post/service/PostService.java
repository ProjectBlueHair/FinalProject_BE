package com.bluehair.hanghaefinalproject.post.service;


import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.Layer;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.common.service.TagExctractor;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.music.repository.MusicRepository;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.InfoPostDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.MainPostDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.MainProfileDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;

import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import com.bluehair.hanghaefinalproject.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.post.mapper.PostMapStruct.POST_MAPPER;
import static com.bluehair.hanghaefinalproject.tag.mapper.TagMapStruct.TAG_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CollaboRequestRepository collaboRequestRepository;
    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    private final TagExctractor tagExctractor;
    @Transactional
    public void createPost(PostDto postDto, String nickname) {

        if (postDto.getPostImage() == null) {
            postDto.setRandomPostImage();
        }

        Post post = POST_MAPPER.PostDtoToPost(postDto, nickname);

        postRepository.save(post);

        // Query 최적화 필요(save < saveall < jpql)
        List<String> hashtagList = tagExctractor.extractHashTags(postDto.getContents());
        List<Tag> tagList = new ArrayList<>();
        for (String s : hashtagList) {
            tagList.add(TAG_MAPPER.stringToTag(s, post));
        }
        tagRepository.saveTagList(tagList);
    }

    @Transactional
    public InfoPostDto infoPost(Long postid) {

        Post post = postRepository.findById(postid).orElseThrow(
                () -> new NotFoundException(Domain.POST, Layer.SERVICE,POST_NOT_FOUND)
        );

        post.viewCount();

        postRepository.save(post);

        return new InfoPostDto(post);
    }


    public List<MainPostDto> mainPost(Pageable pageable) {

        List<MainPostDto> mainPostDtoList = new ArrayList<>();

        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc(pageable);

        for (Post post : postList){

            List<CollaboRequest> collaborateRequestList = collaboRequestRepository.findAllByPostId(post.getId());
            List<MainProfileDto> mainProfile = new ArrayList<>();
            List<String> musicPart = new ArrayList<>();
            List<String> musicFileList = new ArrayList<>();


            for(CollaboRequest collaboRequest : collaborateRequestList){
                List<Music> musiclist = musicRepository.findAllByCollaboRequestId(collaboRequest.getId());
                for (Music music : musiclist){
                    musicFileList.add(music.getMusicFile());
                    musicPart.add(music.getMusicPart());
                }
                Optional<Member> member = memberRepository.findByNickname(collaboRequest.getNickname());
                MainProfileDto mainProfileDto = new MainProfileDto(musicPart, member.get().getProfileImg());
                mainProfile.add(mainProfileDto);
            }
            List<MainProfileDto> mainProfileList = mainProfile.stream().distinct().collect(Collectors.toList());

            mainPostDtoList.add(POST_MAPPER.PostToMainPostDto(post.getId(), post.getTitle(), post.getLikeCount(), post.getViewCount(),musicFileList,mainProfileList));
        }

        return mainPostDtoList;
    }

}
