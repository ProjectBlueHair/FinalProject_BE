package com.bluehair.hanghaefinalproject.post.service;


import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
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
import com.bluehair.hanghaefinalproject.post.exception.NotFoundPostRequestException;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;

import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import com.bluehair.hanghaefinalproject.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                () -> new NotFoundPostRequestException(POST_NOT_FOUND)
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
            List<MainProfileDto> mainProfileDtos = new ArrayList<>();
            List<String> musicList = new ArrayList<>();

            for(CollaboRequest collaboRequest : collaborateRequestList){
                Music music = musicRepository.findByCollaboRequest_Id(collaboRequest.getId());
                Optional<Member> member = memberRepository.findByNickname(collaboRequest.getNickname());
                musicList.add(music.getMusicFile());
                MainProfileDto mainProfileDto = new MainProfileDto(music.getMusicPart(), member.get().getProfileImg());
                mainProfileDtos.add(mainProfileDto);
            }

            mainPostDtoList.add(POST_MAPPER.PostToMainPostDto(post.getId(), post.getTitle(), post.getLikeCount(), post.getViewCount(),musicList,mainProfileDtos));
        }

        return mainPostDtoList;
    }

}
