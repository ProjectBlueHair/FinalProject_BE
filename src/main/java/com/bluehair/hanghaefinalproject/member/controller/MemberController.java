package com.bluehair.hanghaefinalproject.member.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    @ApiOperation(value = "테스트", notes = "테스트용입니다.")
    @GetMapping("/test")
    public void test(){
        System.out.println("controller test api is called..");
    }
}
