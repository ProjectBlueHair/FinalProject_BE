package com.bluehair.hanghaefinalproject.member.service;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.KakaoLoginDto;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import com.bluehair.hanghaefinalproject.member.entity.RefreshToken;
import com.bluehair.hanghaefinalproject.member.entity.Social;
import com.bluehair.hanghaefinalproject.member.repository.MemberDetailRepository;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.member.repository.RefreshTokenRedisRepository;
import com.bluehair.hanghaefinalproject.security.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@PropertySource("classpath:application-OAuth.properties")
public class KakaoService {
    @Value("${kakao.client.id}")
    private String kakaoClientId;
    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final RefreshTokenRedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "?????? ??????"??? "????????? ??????" ??????
        String accessToken = getToken(code);

        // 2. ???????????? ????????? API ?????? : "????????? ??????"?????? "????????? ????????? ??????" ????????????
        KakaoLoginDto kakaoLoginInfo = getKakaoLoginDto(accessToken);

        // 3. ???????????? ????????????
        Member kakaoMember = registerKakaoMemberIfNeeded(kakaoLoginInfo);

        // 4. JWT ?????? ??????
        setNewTokens(response, kakaoMember);
    }

    private void setNewTokens(HttpServletResponse response, Member member) {
        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, jwtUtil.createAccessToken(member.getEmail(), member.getRole()));
        String refreshToken = jwtUtil.createRefreshToken();
        response.addHeader(JwtUtil.AUTHORIZATION_REFRESH, refreshToken);
//        member.updateToken(refreshToken);

        redisRepository.save(new RefreshToken(member.getEmail(), refreshToken));
    }

    // 1. "?????? ??????"??? "????????? ??????" ??????
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body ??????
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        // HTTP ?????? ?????????
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP ?????? (JSON) -> ????????? ?????? ??????
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. ???????????? ????????? API ?????? : "????????? ??????"?????? "????????? ????????? ??????" ????????????
    private KakaoLoginDto getKakaoLoginDto(String accessToken) throws JsonProcessingException {
        // HTTP Header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP ?????? ?????????
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String profileImg = jsonNode.get("properties").get("profile_image").asText();

        return new KakaoLoginDto(id, email, nickname, profileImg);
    }

    private Member registerKakaoMemberIfNeeded(KakaoLoginDto kakaoLoginDto) {
        Member member = memberRepository.findByEmail(kakaoLoginDto.getEmail()).orElse(null);
        if (member == null) {
            String encodedPassword = passwordEncoder.encode(UUID.randomUUID().toString());

            member = Member.builder()
                    .email(kakaoLoginDto.getEmail())
                    .nickname(kakaoLoginDto.getNickname())
                    .profileImg(kakaoLoginDto.getProfileImg())
                    .password(encodedPassword)
                    .build();
            member.updateSocial(Social.KAKAO);

            MemberDetail memberDetail = new MemberDetail(member);
            member.updateMemberDetail(memberDetail);

            memberRepository.save(member);
            memberDetailRepository.save(memberDetail);
            return member;
        }
        if(member.getSocial() == Social.KAKAO) {
            return member;
        }
        member.updateSocial(Social.KAKAO);
        return member;
    }

}
