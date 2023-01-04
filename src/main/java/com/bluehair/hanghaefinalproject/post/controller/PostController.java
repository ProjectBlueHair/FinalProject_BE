package com.bluehair.hanghaefinalproject.post.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.post.dto.requestDto.RequestPostDto;
import com.bluehair.hanghaefinalproject.post.service.PostService;

import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.CREATE_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.INFO_POST;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "작성", notes = "게시글 작성", response = SuccessResponse.class)
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal UserDetails userDetails){

        postService.createPost(requestPostDto.toPostDto(), userDetails.getUsername());

        return SuccessResponse.toResponseEntity(CREATE_POST,null);
    }

    @ApiOperation(value = "조회", notes = "게시글 상세 조회", response = SuccessResponse.class)
    @GetMapping("/{postid}")
    public ResponseEntity<?> infoPost(@PathVariable Long postid){

        return SuccessResponse.toResponseEntity(INFO_POST,postService.infoPost(postid));
    }



}
