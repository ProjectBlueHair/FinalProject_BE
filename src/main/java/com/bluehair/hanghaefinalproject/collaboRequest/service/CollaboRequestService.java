package com.bluehair.hanghaefinalproject.collaboRequest.service;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.ResponseCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;

import static com.bluehair.hanghaefinalproject.collaboRequest.mapper.CollaboRequestMapStruct.COLLABOREQUEST_MAPPER;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.COLLABO_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.music.mapper.MusicMapStruct.MUSIC_MAPPER;

import com.bluehair.hanghaefinalproject.member.entity.Member;
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

import java.util.List;


@Getter
@Service
@RequiredArgsConstructor
public class CollaboRequestService {

    private final CollaboRequestRepository collaboRequestRepository;
    private final MusicRepository musicRepository;
    private final PostRepository postRepository;

    @Transactional
    public void collaboRequest(Long postId, CollaboRequestDetailsDto collaboRequestDetailsDto, SaveMusicDto saveMusicDto, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidCollaboRequestException(POST_NOT_FOUND));

        String nickname = member.getNickname();

        CollaboRequestDto collaboRequestDto = COLLABOREQUEST_MAPPER.CollaboRequestDetailsDtotoCollaboRequestDto(collaboRequestDetailsDto, nickname);
        CollaboRequest collaboRequest = COLLABOREQUEST_MAPPER.CollaboRequestDtotoCollaboRequest(collaboRequestDto, post);

        collaboRequestRepository
                .save(collaboRequest);

        Music music = MUSIC_MAPPER.SaveMusicDtotoMusic(saveMusicDto, collaboRequest);

        musicRepository
                .save(music);

    }

    @Transactional
    public ResponseCollaboRequestDto getCollaboRequest(Long collaborequestid) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaborequestid)
                .orElseThrow(() -> new InvalidCollaboRequestException(COLLABO_NOT_FOUND));
        List<Music> musicList = musicRepository.findAllByCollaboRequest(collaboRequest);
        List<ResponseMusicDto> musicDtoList = MUSIC_MAPPER.MusictoResponseMusicDto(musicList);
        ResponseCollaboRequestDto responseCollaboRequestDto = new ResponseCollaboRequestDto(collaboRequest, musicDtoList);
        return responseCollaboRequestDto;
    }

}
