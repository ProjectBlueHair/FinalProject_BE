package com.bluehair.hanghaefinalproject.webSocket.service;

import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.webSocket.dto.service.MessageDto;
import com.bluehair.hanghaefinalproject.webSocket.dto.service.SaveMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatMessage;
import com.bluehair.hanghaefinalproject.webSocket.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.webSocket.mapper.MessageMapStruct.MESSAGE_MAPPER;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;

    private final MessageRepository messageRepository;

    public SaveMessageDto saveMessage(Long roomId, MessageDto messageDto, String nickname) {

        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.ROOM, SERVICE,MEMBER_NOT_FOUND, "Nickname : "+ nickname)
        );

        SaveMessageDto saveMessageDto = new SaveMessageDto(roomId, messageDto.getMessage(), member.getNickname(), member.getProfileImg()
                                                            ,messageDto.getDate(), messageDto.getTime());

        ChatMessage chatMessage = MESSAGE_MAPPER.SaveMessageDtoToMessage(saveMessageDto);

        messageRepository.save(chatMessage);

        return saveMessageDto;
    }
}
