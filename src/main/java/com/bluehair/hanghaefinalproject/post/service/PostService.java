package com.bluehair.hanghaefinalproject.post.service;


import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.Layer;
import com.bluehair.hanghaefinalproject.common.exception.NotAuthorizedMemberException;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.common.service.TagExctractor;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.music.repository.MusicRepository;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.*;
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

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;
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

        if (postDto.getPostImg() == null) {
            postDto.setRandomPostImg();
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

            String musicFile = post.getMusicFile();

            // 콜라보 리퀘스트 수 만큼 반복
            for(CollaboRequest collaboRequest : collaborateRequestList){

                List<String> musicPart = new ArrayList<>();
                // 콜라보 작성자로 음원 목록 조회
                List<Music> musiclist = musicRepository.findAllByCollaboRequest_Nickname(collaboRequest.getNickname());
                // 음원 수 만큼 반복
                for (Music music : musiclist){
                    musicPart.add(music.getMusicPart());
                }
                // musicPart 중복 제거
                Set<String> set = new HashSet<>(musicPart);
                // List로 다시 변환
                List<String> musicPartList = new ArrayList<>(set);

                Optional<Member> member = memberRepository.findByNickname(collaboRequest.getNickname());
                MainProfileDto mainProfileDto = new MainProfileDto(musicPartList, member.get().getProfileImg(),collaboRequest.getNickname());
                mainProfile.add(mainProfileDto);
            }
            // 닉네임을 기준으로 중복 제거
            Set<MainProfileDto> distinctSet = mainProfile.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MainProfileDto::getNickname))));
            List<MainProfileDto> mainProfileList = distinctSet.stream().collect(Collectors.toList());

            mainPostDtoList.add(POST_MAPPER.PostToMainPostDto(post.getId(), post.getTitle(), post.getLikeCount(), post.getViewCount(),musicFile,mainProfileList));
        }

        return mainPostDtoList;
    }

    public void updatePost(Long postId, PostUpdateDto postUpdateDto, String nickname) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(Domain.POST, Layer.SERVICE,POST_NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_FOUND)
        );
        if (!post.getNickname().equals(member.getNickname())){
            throw new NotAuthorizedMemberException(Domain.POST,Layer.SERVICE,MEMBER_NOT_AUTHORIZED);
        }

        post.update(postUpdateDto.getTitle(), postUpdateDto.getContents(),postUpdateDto.getLyrics(), postUpdateDto.getPostImg());

        postRepository.save(post);
    }
}
