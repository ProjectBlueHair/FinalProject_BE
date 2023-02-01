package com.bluehair.hanghaefinalproject.webSocket.service;

import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.webSocket.dto.response.ResponseMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.dto.response.RoomIdDto;
import com.bluehair.hanghaefinalproject.webSocket.dto.response.RoomListDto;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatMessage;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatRoom;
import com.bluehair.hanghaefinalproject.webSocket.repository.MessageRepository;
import com.bluehair.hanghaefinalproject.webSocket.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.webSocket.mapper.RoomMapStruct.ROOM_MAPPER;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public List<RoomListDto> findAllRoom(String nickname) {

        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.ROOM, SERVICE,MEMBER_NOT_FOUND, "Nickname : " + nickname)
        );

        List<RoomListDto> roomList = new ArrayList<>();

        // 사용자가 member1인 채팅방 조회
        List<ChatRoom> chatRoomList1 = roomRepository.findByMember1_Id(member.getId());

        for (ChatRoom c : chatRoomList1){

            RoomListDto roomListDto = new RoomListDto(c.getRoomId(),c.getMember2().getNickname(), c.getMember2().getProfileImg());
            roomList.add(roomListDto);
        }

        List<ChatRoom> chatRoomList2 = roomRepository.findByMember2_Id(member.getId());

        for (ChatRoom c : chatRoomList2){
            RoomListDto roomListDto = new RoomListDto(c.getRoomId(),c.getMember1().getNickname(), c.getMember1().getProfileImg());
            roomList.add(roomListDto);
        }
        return roomList;
    }
    @Transactional
    public RoomIdDto createRoom(String memberNickname1, String memberNickname2) {

        Member member1 = memberRepository.findByNickname(memberNickname1).orElseThrow(
                () -> new NotFoundException(Domain.ROOM, SERVICE,MEMBER_NOT_FOUND, "Nickname : " + memberNickname2)
        );
        Member member2 = memberRepository.findByNickname(memberNickname2).orElseThrow(
                () -> new NotFoundException(Domain.ROOM, SERVICE,MEMBER_NOT_FOUND, "Nickname : " + memberNickname2)
        );
        Optional<ChatRoom> findRoom1 = roomRepository.findByMember1_IdAndMember2_Id(member1.getId(), member2.getId());
        Optional<ChatRoom> findRoom2 = roomRepository.findByMember1_IdAndMember2_Id(member2.getId(), member1.getId());

        if(findRoom1.isPresent()){
            RoomIdDto roomIdDto = new RoomIdDto(findRoom1.get().getRoomId());
            return roomIdDto;
        }
        if(findRoom2.isPresent()){
            RoomIdDto roomIdDto = new RoomIdDto(findRoom2.get().getRoomId());
            return roomIdDto;
        }

        ChatRoom chatRoom = new ChatRoom();

        if(!findRoom1.isPresent() && !findRoom2.isPresent()){
            chatRoom = ROOM_MAPPER.ChatRoomToRoomDto(member1, member2);
            roomRepository.save(chatRoom);
        }
        RoomIdDto roomIdDto = new RoomIdDto(chatRoom.getRoomId());
        return roomIdDto;
    }

    public List<ResponseMessageDto> entranceRoom(Long roomId) {

        List<ChatMessage> message = messageRepository.findByChatRoom_RoomIdOrderByCreatedAtDesc(roomId);
        List<ResponseMessageDto> messageList = new ArrayList<>();
        for (ChatMessage m : message){
            ResponseMessageDto messageListDto = new ResponseMessageDto(m.getMessage(), m.getNickname(), m.getProfileImg(), m.getDate(), m.getTime());
            messageList.add(messageListDto);
        }

        return messageList;
    }
}