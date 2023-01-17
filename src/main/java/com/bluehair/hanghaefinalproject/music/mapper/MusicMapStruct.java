package com.bluehair.hanghaefinalproject.music.mapper;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import com.bluehair.hanghaefinalproject.music.entity.Music;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MusicMapStruct {
    MusicMapStruct MUSIC_MAPPER = Mappers.getMapper(MusicMapStruct.class);
    default List<ResponseMusicDto> MusictoResponseMusicDto (List<Music> musicList, CollaboRequest collaboRequest){
        List<ResponseMusicDto> responseMusicDtoList = new ArrayList<>();
        for (Music music : musicList) {
            responseMusicDtoList.add(ResponseMusicDto.builder()
                            .music(music)
                            .collaboRequest(collaboRequest)
                            .build());
        }
        return responseMusicDtoList;
    }
}
