package com.bluehair.hanghaefinalproject.member.service;

import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.comment.repository.CommentRepository;
import com.bluehair.hanghaefinalproject.common.exception.*;
import com.bluehair.hanghaefinalproject.common.service.Validator;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMemberInfoDto;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMypageDto;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseSettingDto;
import com.bluehair.hanghaefinalproject.member.dto.serviceDto.*;
import com.bluehair.hanghaefinalproject.member.entity.*;
import com.bluehair.hanghaefinalproject.member.repository.*;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import com.bluehair.hanghaefinalproject.security.exception.CustomJwtException;
import com.bluehair.hanghaefinalproject.security.jwt.JwtUtil;
import com.bluehair.hanghaefinalproject.sse.repository.NotificationRepository;
import com.bluehair.hanghaefinalproject.webSocket.repository.MessageRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;
import static com.bluehair.hanghaefinalproject.member.mapper.MemberMapStruct.MEMBER_MAPPER;
import static com.bluehair.hanghaefinalproject.common.exception.Domain.MEMBER;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final JobRepository jobRepository;

    private final MessageRepository messageRepository;
    private final CollaboRequestRepository collaboRequestRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final PostRepository postRepository;
    private final RefreshTokenRedisRepository redisRepository;
    @Transactional
    public void signUp(SignUpDto signUpDto) {
        memberRepository.findByEmail(signUpDto.getEmail())
                .ifPresent(m->{
                    throw new DuplicationException(MEMBER, SERVICE, DUPLICATED_EMAIL, signUpDto.getEmail());
                });
        memberRepository.findByNickname(signUpDto.getNickname())
                .ifPresent(m-> {
                    throw new DuplicationException(MEMBER, SERVICE, DUPLICATED_NICKNAME, signUpDto.getNickname());
                });

        if(!Validator.isValidEmail(signUpDto.getEmail())){
            throw new FormatException(MEMBER, SERVICE, INVALID_EMAIL, signUpDto.getEmail());
        }
        if(!Validator.isValidPassword(signUpDto.getPassword())){
            throw new FormatException(MEMBER, SERVICE, INVALID_PASSWORD, signUpDto.getPassword());
        }

        signUpDto.encryptPassword(passwordEncoder.encode(signUpDto.getPassword()));

        if (signUpDto.getProfileImg() == null) {
            signUpDto.setRandomProfileImg();
        }

        Member member = MEMBER_MAPPER.SignUpDtoToMember(signUpDto);
        memberRepository.save(member);

        MemberDetail memberDetail = new MemberDetail(member);
        memberDetailRepository.save(memberDetail);

        member.updateMemberDetail(memberDetail);
    }

    @Transactional(readOnly = true)
    public void validateEmail(ValidateEmailDto validateEmailDto) {
        if(!Validator.isValidEmail(validateEmailDto.getEmail())){
            throw new FormatException(MEMBER, SERVICE, INVALID_EMAIL, validateEmailDto.getEmail());
        }
        memberRepository.findByEmail(validateEmailDto.getEmail())
                .ifPresent(m->{
                    throw new DuplicationException(MEMBER, SERVICE, DUPLICATED_EMAIL, validateEmailDto.getEmail());
                });
    }
    @Transactional(readOnly = true)
    public void validateNickname(ValidateNicknameDto validateNicknameDto){
        memberRepository.findByNickname(validateNicknameDto.getNickname())
                .ifPresent(m->{
                    throw new DuplicationException(MEMBER, SERVICE, DUPLICATED_NICKNAME, validateNicknameDto.getNickname());
                });
    }


    private void setNewTokens(HttpServletResponse response, Member member) {
        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, jwtUtil.createAccessToken(member.getEmail(), member.getRole()));
        String refreshToken = jwtUtil.createRefreshToken();
        response.addHeader(JwtUtil.AUTHORIZATION_REFRESH, refreshToken);

        // RefreshToken : MySQL
//        member.updateToken(refreshToken);

        // RefreshToken : Redis Test
        redisRepository.save(new RefreshToken(member.getEmail(), refreshToken));
    }

    @Transactional
    public void login(LoginDto loginDto, HttpServletResponse response) {
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()->new NotFoundException(MEMBER, SERVICE, MEMBER_NOT_FOUND, "Email : " + loginDto.getEmail()));

        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            throw new NotAuthorizedMemberException(MEMBER, SERVICE, PASSWORD_INCORRECT, loginDto.getEmail());
        }

        setNewTokens(response, member);
    }

    @Transactional
    public void tokenReissuance(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtUtil.resolveToken(request, "AccessToken");
        Claims claims = getClaimsFromAccessToken(request, accessToken);

        String refreshToken = jwtUtil.resolveToken(request, "RefreshToken");
        jwtUtil.validateToken(refreshToken, false);

        Member member = memberRepository.findByEmail(claims.getSubject())
                .orElseThrow(()->new NotFoundException(MEMBER, SERVICE, MEMBER_NOT_FOUND, "Email : " + claims.getSubject()));

        // RefreshToken : SQL Repo
//        if(!member.getRefreshToken().substring(7).equals(refreshToken)){
//            throw new CustomJwtException(INVALID_REFRESHTOKEN);
//        }

        // RefreshToken : Redis Test
        RefreshToken redisRefreshToken = redisRepository.findById(member.getEmail())
                .orElseThrow(()-> new NotFoundException(MEMBER, SERVICE, REFRESHTOKEN_NOT_EXIST, "from RedisServer"));
        if(!redisRefreshToken.getToken().substring(7).equals(refreshToken)) {
            throw new CustomJwtException(INVALID_REFRESHTOKEN);
        }

        setNewTokens(response, member);
    }

    private Claims getClaimsFromAccessToken(HttpServletRequest request, String accessToken) {
        try {
            jwtUtil.validateToken(accessToken, true);
        }
        catch (CustomJwtException e){
            if(e.getErrorCode().getCustomHttpStatusCode() == 4015) {
                return jwtUtil.getUserInfoFromHttpServletRequest(request, true);
            }
            jwtUtil.validateToken(accessToken, true);
        }
        return jwtUtil.getUserInfoFromHttpServletRequest(request, true);
    }

    @Transactional
    public ResponseMemberInfoDto memberInfo(CustomUserDetails userDetails){
        return MEMBER_MAPPER.userDetailsToResponseMemberInfoDto(userDetails);
    }

    @Transactional
    public void doFollow(CustomUserDetails userDetails, FollowDto followDto) {
        Member myFollowingMember = memberRepository.findByNickname(followDto.getMyFollowingMemberNickname())
                .orElseThrow(()-> new NotFoundException(MEMBER, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + followDto.getMyFollowingMemberNickname()));

        FollowCompositeKey followCompositeKey
                = new FollowCompositeKey(userDetails.getMember().getId(), myFollowingMember.getId());

        if (followRepository.existsById(followCompositeKey)){
            throw new InvalidRequestException(MEMBER, SERVICE, ALREADY_FOLLWED, myFollowingMember.getNickname());
        }

        Follow follow = new Follow(followCompositeKey, userDetails.getMember(), myFollowingMember);
        followRepository.save(follow);

        memberRepository.updateFollowingCount(userDetails.getMember().getFollowingCount()+1, userDetails.getMember().getId());
        memberRepository.updateFollowerCount(myFollowingMember.getFollowerCount()+1, myFollowingMember.getId());
    }

    @Transactional
    public void doUnfollow(CustomUserDetails userDetails, FollowDto followDto) {
        Member myFollowingMember = memberRepository.findByNickname(followDto.getMyFollowingMemberNickname())
                .orElseThrow(()-> new NotFoundException(MEMBER, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + followDto.getMyFollowingMemberNickname()));

        FollowCompositeKey followCompositeKey
                = new FollowCompositeKey(userDetails.getMember().getId(), myFollowingMember.getId());

        if (!followRepository.existsById(followCompositeKey)){
            throw new InvalidRequestException(MEMBER, SERVICE, ALREADY_UNFOLLWED, myFollowingMember.getNickname());
        }

        followRepository.deleteById(followCompositeKey);

        memberRepository.updateFollowingCount(userDetails.getMember().getFollowingCount()-1, userDetails.getMember().getId());
        memberRepository.updateFollowerCount(myFollowingMember.getFollowerCount()-1, myFollowingMember.getId());
    }

    @Transactional
    public String updateSetting(CustomUserDetails userDetails, SettingMemberDto settingMemberDto, SettingMemberDetailDto settingMemberDetailDto) {
        memberRepository.findByNickname(settingMemberDto.getNickname())
                .ifPresent(m-> {
                    throw new DuplicationException(MEMBER, SERVICE, DUPLICATED_NICKNAME, settingMemberDto.getNickname());
                });

        if (settingMemberDto.getPassword()!=null){
            if(!Validator.isValidPassword(settingMemberDto.getPassword())){
                throw new FormatException(MEMBER, SERVICE, INVALID_PASSWORD, settingMemberDto.getPassword());
            }
            settingMemberDto.encryptPassword(passwordEncoder.encode(settingMemberDto.getPassword()));
        }


        Member member = userDetails.getMember();
        MemberDetail memberDetail = userDetails.getMember().getMemberDetail();

        String before = member.getNickname();

        if(settingMemberDto.getNickname() != null) {
            messageRepository.updateNickname(before, settingMemberDto.getNickname());
            collaboRequestRepository.updateNickname(before, settingMemberDto.getNickname());
            commentRepository.updateNickname(before, settingMemberDto.getNickname());
            postRepository.updateNickname(before, settingMemberDto.getNickname());
//            notificationRepository.updateNickname(before, settingMemberDto.getNickname());
        }

        jobRepository.deleteAllByMemberDetail(userDetails.getMember().getMemberDetail());

        List<Job> jobList = null;
        if (settingMemberDetailDto.getJobList() != null){
             jobList = MEMBER_MAPPER.SettingMemberDetailDtoToJob(settingMemberDetailDto.getJobList(), userDetails.getMember().getMemberDetail());
            jobRepository.saveAll(jobList);
        }

        memberDetail.updateSettings(settingMemberDetailDto, jobList);
        memberDetailRepository.save(memberDetail);

        member.updateSetting(settingMemberDto);
        memberRepository.save(member);

        return member.getNickname();
    }

    @Transactional
    public ResponseSettingDto getSetting(CustomUserDetails userDetails) {
        return MEMBER_MAPPER.memberAndMemberDetailToResponseSettingDto(userDetails.getMember(), userDetails.getMember().getMemberDetail());
    }

    @Transactional
    public ResponseMypageDto getMypage(CustomUserDetails userDetails, String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(()->new NotFoundException(MEMBER, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + nickname));

        Boolean isMine = false;
        Boolean isFollowed = false;

        if (userDetails != null && userDetails.getMember().getNickname().equals(nickname)){
            isMine = true;
        }
        if (userDetails != null) {
            FollowCompositeKey followCompositeKey = new FollowCompositeKey(userDetails.getMember().getId(), member.getId());
            isFollowed = followRepository.existsById(followCompositeKey);
        }

//        return MEMBER_MAPPER.memberAndMemberDetailToResponseMypageDto(member, member.getMemberDetail(), isMine, isFollowed);
        return ResponseMypageDto.builder()
                .member(member)
                .memberDetail(member.getMemberDetail())
                .isFollowed(isFollowed)
                .isMine(isMine)
                .build();
    }
}
