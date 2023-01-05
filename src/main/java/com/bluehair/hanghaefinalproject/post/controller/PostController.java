package com.bluehair.hanghaefinalproject.post.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.post.dto.requestDto.RequestPostDto;
import com.bluehair.hanghaefinalproject.post.service.PostService;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.CREATE_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.INFO_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.MAIN_POST;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "작성", notes = "게시글 작성", response = SuccessResponse.class)
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal CustomUserDetails userDetails){

        postService.createPost(requestPostDto.toPostDto(), userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(CREATE_POST,null);
    }

    @ApiOperation(value = "조회", notes = "게시글 상세 조회", response = SuccessResponse.class)
    @GetMapping("/{postid}")
    public ResponseEntity<?> infoPost(@PathVariable Long postid){

        return SuccessResponse.toResponseEntity(INFO_POST,postService.infoPost(postid));
    }

    @ApiOperation(value = "조회", notes = "게시글 전체 조회", response = SuccessResponse.class)
    @GetMapping("")
    public ResponseEntity<?> mainPost(Pageable pageable){

        return SuccessResponse.toResponseEntity(MAIN_POST,postService.mainPost(pageable));
    }



}
