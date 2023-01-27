package com.bluehair.hanghaefinalproject.webSocket.mapper;



import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapStruct {
    RoomMapStruct ROOM_MAPPER = Mappers.getMapper(RoomMapStruct.class);

    ChatRoom ChatRoomToRoomDto(Member member1, Member member2);
}
