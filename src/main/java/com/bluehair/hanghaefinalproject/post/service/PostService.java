package com.bluehair.hanghaefinalproject.post.service;


import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.comment.repository.CommentRepository;
import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.NotAuthorizedMemberException;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.common.service.TagExctractor;
import com.bluehair.hanghaefinalproject.like.entity.PostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.like.repository.CommentLikeRepository;
import com.bluehair.hanghaefinalproject.like.repository.PostLikeRepository;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.music.repository.MusicRepository;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.*;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;

import com.bluehair.hanghaefinalproject.sse.repository.NotificationRepository;
import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import com.bluehair.hanghaefinalproject.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

import static com.bluehair.hanghaefinalproject.common.exception.Domain.POST;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;
import static com.bluehair.hanghaefinalproject.post.mapper.PostMapStruct.POST_MAPPER;
import static com.bluehair.hanghaefinalproject.tag.mapper.TagMapStruct.TAG_MAPPER;
import static com.bluehair.hanghaefinalproject.music.mapper.MusicMapStruct.MUSIC_MAPPER;

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
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    private final NotificationRepository notificationRepository;

    @Transactional
    public Long createPost(PostDto postDto, String nickname) {

        if (postDto.getPostImg() == null) {
            postDto.setRandomPostImg();
        }

        Post post = POST_MAPPER.PostDtoToPost(postDto, nickname);

        postRepository.save(post);

        if (postDto.getContents() != null && !Objects.equals(postDto.getContents(), "") && !Objects.equals(postDto.getContents(), " ")){
            List<String> hashtagList = tagExctractor.extractHashTags(postDto.getContents());
            saveHashtagList(post, hashtagList);
        }
        return post.getId();
    }

    private void saveHashtagList(Post post, List<String> hashtagList) {
        if (hashtagList.size() != 0){
            List<Tag> tagList = new ArrayList<>();
            for (String s : hashtagList) {
                tagList.add(TAG_MAPPER.stringToTag(s, post));
            }
            // Query 최적화 필요(save < saveall < jpql)
            tagRepository.saveTagList(tagList);
        }
    }

    @Transactional
    public InfoPostDto infoPost(Long postid, Member member) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new NotFoundException(POST, SERVICE,POST_NOT_FOUND)
        );

        post.viewCount();

        postRepository.save(post);

        Boolean isLiked = false;

        if(member!=null){
        Member postMember = memberRepository.findByNickname(post.getNickname()).orElseThrow(
                () -> new NotFoundException(POST, SERVICE, MEMBER_NOT_FOUND)
        );

        PostLikeCompositeKey postLikeCompositeKey
                = new PostLikeCompositeKey(member.getId(), postMember.getId() );
        if (postLikeRepository.findById(postLikeCompositeKey).isPresent()){
            isLiked = true;
        }
        }

        return new InfoPostDto(post, isLiked);
    }
    public List<MainPostDto>  myPost(Pageable pageable, String nickname) {
        List<MainPostDto> mainPostDtoList = new ArrayList<>();
        List<Post> postList = postRepository.findByNickname(pageable,nickname);
        for (Post post : postList){
            List<CollaboRequest> collaboRequestList = collaboRequestRepository.findAllByPostId(post.getId());

            List<MainProfileDto> mainProfile = new ArrayList<>();

            List<Tag> tagGet = tagRepository.findAllByPostId(post.getId());

            List<String> tagList = new ArrayList<>();

            for(Tag tag : tagGet){
                tagList.add(tag.getContents());
            }

            String musicFile = post.getMusicFile();

            for (CollaboRequest collaboRequest : collaboRequestList){
                List<String> musicPart = new ArrayList<>();
                List<Music> musicList = musicRepository.findAllByCollaboRequest_Nickname(collaboRequest.getNickname());
                for (Music music : musicList){
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
            Set<MainProfileDto> distinctSet = mainProfile.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MainProfileDto::getNickname))));
            List<MainProfileDto> mainProfileList = distinctSet.stream().collect(Collectors.toList());

            mainPostDtoList.add(POST_MAPPER.PostToMainPostDto(post.getId(), post.getTitle(),post.getPostImg(), post.getLikeCount(), post.getViewCount(),musicFile,tagList,mainProfileList));
        }
        return mainPostDtoList;
    }

    public List<MainPostDto> mainPost(Pageable pageable, String search) {

        List<MainPostDto> mainPostDtoList = new ArrayList<>();
        List<Post> postList = new ArrayList<>();
        String searchContents = search;
        if (search == null || search.equals(" ")) {
            postList = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        if (search != null) {
            if (search.charAt(0) != '#') {
                postList = postRepository.findByTitleContainsOrContentsContains(pageable, search, searchContents);
            }
            if (search.charAt(0) == '#') {
                postList = postRepository.findByContentsContains(pageable, search);
            }
        }

        for (Post post : postList){

            List<CollaboRequest> collaborateRequestList = collaboRequestRepository.findAllByPostId(post.getId());

            List<MainProfileDto> mainProfile = new ArrayList<>();

            List<Tag> tagGet = tagRepository.findAllByPostId(post.getId());

            List<String> tagList = new ArrayList<>();

            for (Tag tag : tagGet){
                tagList.add(tag.getContents());
            }

            String musicFile = post.getMusicFile();

            // 콜라보 리퀘스트 수 만큼 반복
            for(CollaboRequest collaboRequest : collaborateRequestList){

                List<String> musicPart = new ArrayList<>();
                // 콜라보 작성자로 음원 목록 조회
                List<Music> musicList = musicRepository.findAllByCollaboRequest_Nickname(collaboRequest.getNickname());
                // 음원 수 만큼 반복
                for (Music music : musicList){
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

            mainPostDtoList.add(POST_MAPPER.PostToMainPostDto(post.getId(), post.getTitle(),post.getPostImg(), post.getLikeCount(), post.getViewCount(),musicFile,tagList,mainProfileList));
        }

        return mainPostDtoList;
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto, String nickname) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(POST, SERVICE,POST_NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT, SERVICE,MEMBER_NOT_FOUND)
        );
        if (!post.getNickname().equals(member.getNickname())){
            throw new NotAuthorizedMemberException(POST, SERVICE,MEMBER_NOT_AUTHORIZED);
        }

        post.update(postUpdateDto.getTitle(), postUpdateDto.getContents(),postUpdateDto.getCollaboNotice(), postUpdateDto.getPostImg());

        postRepository.save(post);
    }

    public List<ResponseMusicDto> musicPost(Long postId){
        List<ResponseMusicDto> responseMusicDtoList = new ArrayList<>();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(POST, SERVICE,POST_NOT_FOUND)
        );
        for (CollaboRequest collaboRequest : post.getCollaboRequestList()) {
            if (collaboRequest.getApproval()){
                responseMusicDtoList.addAll(MUSIC_MAPPER.MusictoResponseMusicDto(collaboRequest.getMusicList(), collaboRequest));
            }
        }
        return responseMusicDtoList;
    }


    public void deletePost(Long postId, String nickname) {

        // 게시글 검색
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new NotFoundException(POST, SERVICE,POST_NOT_FOUND)
        );

        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT, SERVICE,MEMBER_NOT_FOUND)
        );

        if (!post.getNickname().equals(member.getNickname())){
            throw new NotAuthorizedMemberException(POST, SERVICE,MEMBER_NOT_AUTHORIZED);
        }

        // 해당 게시글 콜라보 리스트 조회
        List<CollaboRequest> collaboRequestList = collaboRequestRepository.findAllByPostId(postId);
        // 콜라보 리스트 각각의 뮤직 삭제
        for(CollaboRequest collaboRequest : collaboRequestList){
            musicRepository.deleteAllByCollaboRequest(collaboRequest);
        }
        // 해당 게시글의 콜라보 삭제
        collaboRequestRepository.deleteAllByPost(post);

        // 해당 게시글의 댓글 리스트 조회
        List<Comment> commentList = commentRepository.findByPostId(postId);

        // 댓글 각각의 좋아요 삭제
        for (Comment comment : commentList){
            commentLikeRepository.deleteAllByComment(comment);
        }
        // 해당 게시글의 태그 삭제
        tagRepository.deleteAllByPost(post);
        // 게시글 좋아요 삭제
        postLikeRepository.deleteAllByPost(post);
        // 해당 게시글의 댓글 삭제
        commentRepository.deleteAllByPost(post);
        // 게시글 삭제
        postRepository.deleteById(postId);


    }
}
