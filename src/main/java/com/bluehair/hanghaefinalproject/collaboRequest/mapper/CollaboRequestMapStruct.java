package com.bluehair.hanghaefinalproject.collaboRequest.mapper;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CollaboRequestMapStruct {
    CollaboRequestMapStruct COLLABOREQUEST_MAPPER = Mappers.getMapper(CollaboRequestMapStruct.class);

    CollaboRequestDto CollaboRequestDetailsDtotoCollaboRequestDto(CollaboRequestDetailsDto collaboRequestDetailsDto, String nickname);

    default CollaboRequest CollaboRequestDtotoCollaboRequest(CollaboRequestDto collaboRequestDto, Post post){
        return CollaboRequest.builder()
                .title(collaboRequestDto.getTitle())
                .contents(collaboRequestDto.getContents())
                .activated(collaboRequestDto.getActivated())
                .approval(collaboRequestDto.getApproval())
                .nickname(collaboRequestDto.getNickname())
                .post(post)
                .build();
    }

}

