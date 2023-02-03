package com.bluehair.hanghaefinalproject.member.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.member.dto.requestDto.*;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMemberInfoDto;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMypageDto;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseSettingDto;
import com.bluehair.hanghaefinalproject.member.service.KakaoService;
import com.bluehair.hanghaefinalproject.member.service.MemberService;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.*;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final KakaoService kakaoService;
    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "사용 가능한 이메일"),
            @ApiResponse(responseCode = "4090", description = "이메일 중복"),
            @ApiResponse(responseCode = "4000", description = "유효하지 않은 이메일")
    })
    @Operation(summary = "이메일 검증", description = "이메일 중복 확인, 이메일 형식 확인")
    @PostMapping("/validate/email")
    public ResponseEntity<SuccessResponse<Object>> validateEmail(@RequestBody RequestValidateEmailDto requestValidateEmailDto) {
        memberService.validateEmail(requestValidateEmailDto.toValidateEmailDto());
        return SuccessResponse.toResponseEntity(VALID_EMAIL, null);
    }
    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "사용 가능한 닉네임"),
            @ApiResponse(responseCode = "4090", description = "닉네임 중복"),
    })
    @Operation(summary = "닉네임 검증", description = "닉네임 중복 확인")
    @PostMapping("validate/nickname")
    public ResponseEntity<SuccessResponse<Object>> validateNickname(@RequestBody RequestValidateNicknameDto requestValidateNicknameDto) {
        memberService.validateNickname(requestValidateNicknameDto.toValidateNicknameDto());
        return SuccessResponse.toResponseEntity(VALID_NICKNAME, null);
    }
    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "4000", description = "유효하지 않은 비밀번호"),
            @ApiResponse(responseCode = "4090-1", description = "이메일 중복"),
            @ApiResponse(responseCode = "4090-2", description = "닉네임 중복")
    })
    @Operation(summary = "회원 가입", description = "이메일 및 닉네임 중복 확인, Password Encrypt, DB 저장")
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<Object>> signUp(@RequestBody RequestSignUpDto requestSignUpDto) {
        memberService.signUp(requestSignUpDto.toSignUpDto());
        return SuccessResponse.toResponseEntity(SIGNUP_MEMBER, null);
    }
    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "로그인 성공"),
            @ApiResponse(responseCode = "4000", description = "계정 정보 불일치")
    })
    @Operation(summary = "일반 회원 로그인", description = "계정 비밀번호 일치 여부 확인")
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Object>> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse response) {
        memberService.login(requestLoginDto.toLoginMemberDto(), response);
        return SuccessResponse.toResponseEntity(LOGIN_MEMBER, null);
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<SuccessResponse<Object>> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        kakaoService.kakaoLogin(code, response);
        return SuccessResponse.toResponseEntity(LOGIN_MEMBER, null);
    }

    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "회원 정보 반환 성공"),
            @ApiResponse(responseCode = "4011", description = "AccessToken 존재하지 않음"),
            @ApiResponse(responseCode = "4013", description = "유효하지 않은 AccessToken"),
            @ApiResponse(responseCode = "4015", description = "만료된 AccessToken")
    })
    @Operation(summary = "현재 접속 중인 회원 정보 반환", description = "토큰 분해 및 정보 반환")
    @GetMapping("/info")
    public ResponseEntity<SuccessResponse<ResponseMemberInfoDto>> memberInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return SuccessResponse.toResponseEntity(MEMBER_INFO, memberService.memberInfo(userDetails));
    }
    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "4011", description = "AccessToken 존재하지 않음"),
            @ApiResponse(responseCode = "4013", description = "유효하지 않은 AccessToken"),
            @ApiResponse(responseCode = "4015", description = "만료된 AccessToken"),
            @ApiResponse(responseCode = "4012", description = "RefreshToken 존재하지 않음"),
            @ApiResponse(responseCode = "4014", description = "유효하지 않은 RefreshToken"),
            @ApiResponse(responseCode = "4016", description = "만료된 RefreshToken")
    })
    @Operation(summary = "토큰 재발행", description = "Access, Response Token 유효성 검사 및 토큰 재발행")
    @PostMapping("/reissuance")
    public ResponseEntity<SuccessResponse<Object>> reissuance(HttpServletRequest request, HttpServletResponse response){
        memberService.tokenReissuance(request, response);
        return SuccessResponse.toResponseEntity(TOKEN_REISSUANCE, null);
    }
    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000-1", description = "팔로우 성공"),
            @ApiResponse(responseCode = "2000-2", description = "언팔로우 성공"),
            @ApiResponse(responseCode = "4011", description = "AccessToken 존재하지 않음"),
            @ApiResponse(responseCode = "4013", description = "유효하지 않은 AccessToken"),
            @ApiResponse(responseCode = "4015", description = "만료된 AccessToken"),
            @ApiResponse(responseCode = "4040-1", description = "이미 팔로우한 회원"),
            @ApiResponse(responseCode = "4040-2", description = "이미 언팔로우한 회원")
    })
    @Operation(summary = "팔로우 기능", description = "Follow 시 isFollowed=false, Unfollow 시 isFollowed=true")
    @PutMapping("/follow")
    public ResponseEntity<SuccessResponse<Object>> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                          @RequestBody RequestFollowDto requestFollowDto) {
        if(requestFollowDto.getIsFollowed()){
            memberService.doUnfollow(customUserDetails, requestFollowDto.toFollowDto());
            return SuccessResponse.toResponseEntity(UNFOLLOW_MEMBER, null);
        }
        memberService.doFollow(customUserDetails, requestFollowDto.toFollowDto());
        return SuccessResponse.toResponseEntity(FOLLOW_MEMBER, null);
    }

    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "계정 설정 업데이트 성공"),
            @ApiResponse(responseCode = "4000", description = "유효하지 않은 비밀번호")
    })
    @Operation(summary = "계정 설정 업데이트", description = "계정 설정 업데이트")
    @PutMapping("/setting")
    public ResponseEntity<SuccessResponse<Object>> updateSetting(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                 @RequestBody RequestSettingDto requestSettingDto){
        String nickname = memberService.updateSetting(customUserDetails, requestSettingDto.requestSettingDtoToSettingMemberDto(),
                requestSettingDto.requestSettingDtoToSettingMemberDetailDto());
        return SuccessResponse.toResponseEntity(MEMBER_UPDATE_SETTING, nickname);
    }

    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "계정 설정 불러오기 성공")
    })
    @Operation(summary = "계정 설정 불러오기", description = "계정 설정 불러오기")
    @GetMapping("/setting")
    public ResponseEntity<SuccessResponse<ResponseSettingDto>> getSetting(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return SuccessResponse.toResponseEntity(MEMBER_GET_SETTING, memberService.getSetting(customUserDetails));
    }

    @Tag(name = "Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "마이페이지 유저 정보 불러오기 성공")
    })
    @Operation(summary = "마이페이지 유저 정보 불러오기", description = "본인일 경우 isMine = true")
    @GetMapping("/mypage/{nickname}")
    public ResponseEntity<SuccessResponse<ResponseMypageDto>> getMypage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                        @PathVariable String nickname) {
        String encodedNickname = URLDecoder.decode(nickname, StandardCharsets.UTF_8);
        return SuccessResponse.toResponseEntity(MEMBER_GET_SETTING, memberService.getMypage(customUserDetails, encodedNickname));
    }
}
