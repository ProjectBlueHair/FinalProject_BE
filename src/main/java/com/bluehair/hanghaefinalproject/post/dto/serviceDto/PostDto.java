package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

import java.util.Random;

@Getter
public class PostDto {
    private String title;
    private String contents;
    private String lyrics;
    private String postImg;
    private String tag;

    @Builder
    public PostDto(String title, String contents, String lyrics,
                String postImg,String tag){
        this.title = title;
        this.contents = contents;
        this.lyrics = lyrics;
        this.postImg = postImg;
        this.tag = tag;
    }

    public void setRandomPostImg() {
        String[] RandomPostImage = new String[10];
        RandomPostImage[0] = "https://images.unsplash.com/photo-1525351326368-efbb5cb6814d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1780&q=80";
        RandomPostImage[1] = "https://images.unsplash.com/photo-1511268559489-34b624fbfcf5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80";
        RandomPostImage[2] = "https://images.unsplash.com/photo-1522479764535-46d1a39bdd28?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
        RandomPostImage[3] = "https://images.unsplash.com/photo-1645381718361-a05f5fef783c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=701&q=80";
        RandomPostImage[4] = "https://images.unsplash.com/photo-1442507210693-938e0e77fef2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80";
        RandomPostImage[5] = "https://images.unsplash.com/photo-1487631807774-f9faa5694358?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1112&q=80";
        RandomPostImage[6] = "https://images.unsplash.com/photo-1521674685714-2446ff917fbc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
        RandomPostImage[7] = "https://images.unsplash.com/photo-1528916653107-a7a7e52cae01?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
        RandomPostImage[8] = "https://images.unsplash.com/photo-1505075123357-35a9138620d9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
        RandomPostImage[9] = "https://images.unsplash.com/photo-1467664631004-58beab1ece0d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";

        Random random = new Random();
        this.postImg= RandomPostImage[random.nextInt(10)];
    }

}
