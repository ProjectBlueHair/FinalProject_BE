package com.bluehair.hanghaefinalproject.webSocket.service;

import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.webSocket.dto.service.MessageDto;
import com.bluehair.hanghaefinalproject.webSocket.dto.service.SaveMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatMessage;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatRoom;
import com.bluehair.hanghaefinalproject.webSocket.repository.MessageRepository;
import com.bluehair.hanghaefinalproject.webSocket.repository.RoomRepository;
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
    private final RoomRepository roomRepository;

    public SaveMessageDto saveMessage(Long roomId, MessageDto messageDto, String nickname) {

        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.ROOM, SERVICE,MEMBER_NOT_FOUND, "Nickname : "+ nickname)
        );

        SaveMessageDto saveMessageDto = new SaveMessageDto(roomId, messageDto.getMessage(), member.getNickname(), member.getProfileImg()
                                                            ,messageDto.getDate(), messageDto.getTime());

        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 ChatRoom"));

        ChatMessage chatMessage = ChatMessage.builder()
                .message(saveMessageDto.getMessage())
                .profileImg(saveMessageDto.getProfileImg())
                .time(saveMessageDto.getTime())
                .nickname(saveMessageDto.getNickname())
                .chatRoom(chatRoom)
                .date(saveMessageDto.getDate())
                .build();

        messageRepository.save(chatMessage);

        return saveMessageDto;
    }
}
