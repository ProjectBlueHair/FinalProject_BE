package com.bluehair.hanghaefinalproject.collaboRequest.service;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDto;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.ResponseCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestListForPostDto;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;

import static com.bluehair.hanghaefinalproject.collaboRequest.mapper.CollaboRequestMapStruct.COLLABOREQUEST_MAPPER;
import static com.bluehair.hanghaefinalproject.common.exception.Domain.COLLABO_REQUEST;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;
import static com.bluehair.hanghaefinalproject.music.mapper.MusicMapStruct.MUSIC_MAPPER;

import com.bluehair.hanghaefinalproject.common.exception.*;
import com.bluehair.hanghaefinalproject.member.entity.FollowCompositeKey;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.FollowRepository;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.music.repository.MusicRepository;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;

import com.bluehair.hanghaefinalproject.sse.entity.NotificationType;
import com.bluehair.hanghaefinalproject.sse.entity.RedirectionType;
import com.bluehair.hanghaefinalproject.sse.service.NotificationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Getter
@Service
@RequiredArgsConstructor
public class CollaboRequestService {

    private final CollaboRequestRepository collaboRequestRepository;
    private final MusicRepository musicRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    private final FollowRepository followRepository;

    @Transactional
    public Long collaboRequest(Long postId, CollaboRequestDetailsDto collaboRequestDetailsDto, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, POST_NOT_FOUND, "Post ID : " + postId));

        String nickname = member.getNickname();

        CollaboRequestDto collaboRequestDto = COLLABOREQUEST_MAPPER.CollaboRequestDetailsDtotoCollaboRequestDto(collaboRequestDetailsDto, nickname);
        CollaboRequest collaboRequest = COLLABOREQUEST_MAPPER.CollaboRequestDtotoCollaboRequest(collaboRequestDto, post);

        collaboRequestRepository.save(collaboRequest);

        //post 작성자에게 콜라보 요청 알림 - 콜라보 상세 조회로 이동
        Member postMember = memberRepository.findByNickname(post.getNickname())
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + post.getNickname()));
        Long collaboId = collaboRequest.getId();
        String content = post.getTitle()+"에 대한 콜라보 요청이 있습니다.";
        notificationService.send(postMember, member, NotificationType.COLLABO_REQUEST, content, RedirectionType.collaboRequested, collaboId, postId);

        return collaboRequest.getId();
    }

    @Transactional
    public ResponseCollaboRequestDto getCollaboRequest(Long collaborequestid) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, COLLABO_NOT_FOUND, "CollbaoRequest ID : " + collaborequestid));
        List<Music> musicList = musicRepository.findAllByCollaboRequest(collaboRequest);
        List<ResponseMusicDto> musicDtoList = MUSIC_MAPPER.MusictoResponseMusicDto(musicList, collaboRequest);

        return new ResponseCollaboRequestDto(collaboRequest, musicDtoList);
    }

    @Transactional
    public List<CollaboRequestListForPostDto> getCollaboRequestList(Long postid, Member member) {
        Post post = postRepository.findById(postid)
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, POST_NOT_FOUND, "Post ID : " + postid));

        List<CollaboRequestListForPostDto> collaboRequestListForPostDto = new ArrayList<>();
        List<CollaboRequest> collaboRequestList = collaboRequestRepository.findAllByPost(post);

        for (CollaboRequest collaboRequest : collaboRequestList) {
            if (collaboRequest.getApproval()) {
                List<String> musicPartsList = new ArrayList<>();
                List<Music> musiclist = musicRepository.findAllByCollaboRequestId(collaboRequest.getId());

                for (Music music : musiclist) {
                    musicPartsList.add(music.getMusicPart());
                }

                Member collabomember = memberRepository.findByNickname(collaboRequest.getNickname())
                        .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + collaboRequest.getNickname()));
                String profileImg = collabomember.getProfileImg();
                Long followerCount = collabomember.getFollowerCount();
                Boolean isFollowed = false;

                if(member!=null){
                FollowCompositeKey followCompositeKey
                        = new FollowCompositeKey(member.getId(), collabomember.getId());
                if (followRepository.existsById(followCompositeKey)){
                   isFollowed = true;
                }
                }

                collaboRequestListForPostDto.add(COLLABOREQUEST_MAPPER.CollaboRequestListtoCollaboRequestListDto(collaboRequest, followerCount, isFollowed, profileImg,  musicPartsList));
            }
        }

        return collaboRequestListForPostDto;
    }

    @Transactional
    public void approveCollaboRequest(Long collaborequestid, Member member) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, COLLABO_NOT_FOUND, "CollaboRequest ID : " + collaborequestid));
        Post post = postRepository.findById(collaboRequest.getPost().getId())
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, POST_NOT_FOUND, "Post ID : " + collaboRequest.getPost().getId()));
        if (!post.getNickname().equals(member.getNickname())) {
            throw new NotAuthorizedMemberException(COLLABO_REQUEST, SERVICE, MEMBER_NOT_AUTHORIZED, member.getNickname());
        }

        Boolean approval = true;
        collaboRequest.approve(approval);
        collaboRequestRepository.save(collaboRequest);

        //요청한 사람한테 승인 완료 알림 - 게시글 상세 조회로 이동
        Long postId = post.getId();
        Member collaboMember = memberRepository.findByNickname(collaboRequest.getNickname())
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + collaboRequest.getNickname()));
        if (!collaboMember.getNickname().equals(member.getNickname())) {
            String content = post.getTitle() + "에 대한 콜라보 요청이 승인되었습니다.";
            notificationService.send(collaboMember, member, NotificationType.COLLABO_APPROVED, content, RedirectionType.detail, postId, null);
        }
    }

    @Transactional
    public void deleteCollaboRequest(Long collaborequestid, Member member) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, COLLABO_NOT_FOUND, "CollaboRequest ID : " + collaborequestid));

        checkCollaboMember(member, collaboRequest);

        collaboRequestRepository.delete(collaboRequest);
    }

    @Transactional
    public void updateCollaboRequest(Long collaborequestid,
                                     CollaboRequestDetailsDto collaboRequestDetailsDto,
                                     Member member) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, COLLABO_NOT_FOUND, "CollaboRequest ID : " + collaborequestid));

        checkCollaboMember(member, collaboRequest);

        CollaboRequestDto collaboRequestDto = COLLABOREQUEST_MAPPER.CollaboRequestDetailsDtotoUpdate(collaboRequestDetailsDto);
        collaboRequest.update(collaboRequestDto);
        collaboRequestRepository.save(collaboRequest);
    }

    private static void checkCollaboMember(Member member, CollaboRequest collaboRequest) {
        String nickname = collaboRequest.getNickname();
        if(!nickname.equals(member.getNickname())){
            throw new NotAuthorizedMemberException(COLLABO_REQUEST, SERVICE, MEMBER_NOT_AUTHORIZED, member.getNickname());
        }
        if(collaboRequest.getApproval()){
            throw new InvalidRequestException(COLLABO_REQUEST, SERVICE, COLLABO_ALREADY_APPROVED, "CollaboRequest Status : " + collaboRequest.getApproval());
        }
    }
}
