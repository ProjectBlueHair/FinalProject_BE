package com.bluehair.hanghaefinalproject.collaboRequest.mapper;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestListForPostDto;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    default CollaboRequestListForPostDto CollaboRequestListtoCollaboRequestListDto(CollaboRequest collaboRequest,
                                                                           String profileImg,
                                                                           List<String> musicPartsList){
        return CollaboRequestListForPostDto.builder()
                .collaboId(collaboRequest.getId())
                .title(collaboRequest.getTitle())
                .nickname(collaboRequest.getNickname())
                .profileImg(profileImg)
                .createdAt(collaboRequest.getCreatedAt())
                .modifiedAt(collaboRequest.getModifiedAt()).musicPartsList(musicPartsList)
                .build();
    }



}

