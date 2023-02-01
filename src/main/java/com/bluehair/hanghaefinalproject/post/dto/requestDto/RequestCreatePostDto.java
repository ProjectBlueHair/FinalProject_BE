package com.bluehair.hanghaefinalproject.post.dto.requestDto;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.requestDto.RequestCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.serviceDto.CollaboRequestDetailsDto;
import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "Facade Post 작성 Request DTO")
@Getter
public class RequestCreatePostDto {
    private RequestPostDto requestPostDto;
    private RequestCollaboRequestDto requestCollaboRequestDto;

    public PostDto toPostDto() {
        return PostDto.builder()
                .title(requestPostDto.getTitle())
                .contents(requestPostDto.getContents())
                .collaboNotice(requestPostDto.getCollaboNotice())
                .postImg(requestPostDto.getPostImg())
                .build();
    }

    public CollaboRequestDetailsDto toCollaboRequestDetailsDto() {
        return CollaboRequestDetailsDto.builder()
                .contents(requestCollaboRequestDto.getContents())
                .build();
    }

    public List<String> toMusicPartList() {
        return requestCollaboRequestDto.musicPartList;
    }
}
