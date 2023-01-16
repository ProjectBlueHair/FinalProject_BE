package com.bluehair.hanghaefinalproject.music.dto;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMusicDto {
    @Schema(description = "음원 샘플 URL", example = "http://s3-asdfkjlasdgflkajgl")
    private String musicFile;
    @Schema(description = "음원 파트", example = "Bass")
    private String musicPart;
    @Schema(description = "작성자 닉네임", example = "user01")
    private String nickname;
    @Builder
    public ResponseMusicDto(Music music, CollaboRequest collaboRequest) {
        this.musicFile = music.getMusicFile();
        this.musicPart = music.getMusicPart();
        this.nickname = collaboRequest.getNickname();
    }
}
