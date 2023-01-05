package com.bluehair.hanghaefinalproject.music.mapper;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.dto.SaveMusicDto;
import com.bluehair.hanghaefinalproject.music.entity.Music;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicMapStruct {
    MusicMapStruct MUSIC_MAPPER = Mappers.getMapper(MusicMapStruct.class);
    Music SaveMusicDtotoMusic (SaveMusicDto saveMusicDto, CollaboRequest collaboRequest);
}
