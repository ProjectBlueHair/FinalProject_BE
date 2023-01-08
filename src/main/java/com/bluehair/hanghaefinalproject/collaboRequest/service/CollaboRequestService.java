package com.bluehair.hanghaefinalproject.collaboRequest.service;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDto;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.ResponseCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestListForPostDto;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.exception.NotAuthorizedtoApproveException;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;

import static com.bluehair.hanghaefinalproject.collaboRequest.mapper.CollaboRequestMapStruct.COLLABOREQUEST_MAPPER;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;
import static com.bluehair.hanghaefinalproject.music.mapper.MusicMapStruct.MUSIC_MAPPER;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.music.dto.MusicDto;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import com.bluehair.hanghaefinalproject.music.dto.SaveMusicDto;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.music.repository.MusicRepository;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.collaboRequest.exception.InvalidCollaboRequestException;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
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

    @Transactional
    public void collaboRequest(Long postId, CollaboRequestDetailsDto collaboRequestDetailsDto, SaveMusicDto saveMusicDto, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidCollaboRequestException(POST_NOT_FOUND));

        String nickname = member.getNickname();

        CollaboRequestDto collaboRequestDto = COLLABOREQUEST_MAPPER.CollaboRequestDetailsDtotoCollaboRequestDto(collaboRequestDetailsDto, nickname);
        CollaboRequest collaboRequest = COLLABOREQUEST_MAPPER.CollaboRequestDtotoCollaboRequest(collaboRequestDto, post);

        collaboRequestRepository
                .save(collaboRequest);
        List<MusicDto> musicList = saveMusicDto.getMusicDtoList();
        for (MusicDto musicDto : musicList) {
            Music music = MUSIC_MAPPER.MusicDtotoMusic(musicDto, collaboRequest);
            musicRepository
                    .save(music);
        }
    }

    @Transactional
    public ResponseCollaboRequestDto getCollaboRequest(Long collaborequestid) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new InvalidCollaboRequestException(COLLABO_NOT_FOUND));
        List<Music> musicList = musicRepository.findAllByCollaboRequest(collaboRequest);
        List<ResponseMusicDto> musicDtoList = MUSIC_MAPPER.MusictoResponseMusicDto(musicList);

        return new ResponseCollaboRequestDto(collaboRequest, musicDtoList);
    }


    public List<CollaboRequestListForPostDto> getCollaboRequestList(Long postid) {
        Post post = postRepository.findById(postid)
                .orElseThrow(() -> new InvalidCollaboRequestException(POST_NOT_FOUND));

        List<CollaboRequestListForPostDto> collaboRequestListForPostDto = new ArrayList<>();
        List<CollaboRequest> collaboRequestList = collaboRequestRepository.findAllByPost(post);

        for (CollaboRequest collaboRequest : collaboRequestList) {
            if (collaboRequest.getApproval()) {
                List<String> musicPartsList = new ArrayList<>();
                List<Music> musiclist = musicRepository.findAllByCollaboRequestId(collaboRequest.getId());

                for (Music music : musiclist) {
                    musicPartsList.add(music.getMusicPart());
                }

                Member member = memberRepository.findByNickname(collaboRequest.getNickname())
                        .orElseThrow(() -> new InvalidCollaboRequestException(MEMBER_NOT_FOUND));
                String profileImg = member.getProfileImg();

                collaboRequestListForPostDto.add(COLLABOREQUEST_MAPPER.CollaboRequestListtoCollaboRequestListDto(collaboRequest, profileImg, musicPartsList));
            }
        }
        return collaboRequestListForPostDto;
    }

    public void approveCollaboRequest(Long collaborequestid, Member member) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new InvalidCollaboRequestException(COLLABO_NOT_FOUND)
        );
        Post post = postRepository.findById(collaboRequest.getPost().getId())
                .orElseThrow(() -> new InvalidCollaboRequestException(POST_NOT_FOUND));
        if (!post.getNickname().equals(member.getNickname())){
            throw new NotAuthorizedtoApproveException(MEMBER_NOT_AUTHORIZED);
        }

        Boolean approval = true;
        collaboRequest.approve(approval);
        collaboRequestRepository.save(collaboRequest);
    }
}
