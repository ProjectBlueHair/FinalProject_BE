package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CollaboRequestListDto {
    List<ResponseCollaboRequestDto> responseCollaboRequestDtoList = new ArrayList<>();

    public void addResponseCollaboRequestDto(ResponseCollaboRequestDto responseCollaboRequestDto){
        this.responseCollaboRequestDtoList.add(responseCollaboRequestDto);
    }
}
