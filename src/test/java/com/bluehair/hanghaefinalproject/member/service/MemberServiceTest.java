package com.bluehair.hanghaefinalproject.member.service;

import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.common.exception.DuplicationException;
import com.bluehair.hanghaefinalproject.common.exception.FormatException;
import com.bluehair.hanghaefinalproject.common.exception.GlobalExceptionHandler;
import com.bluehair.hanghaefinalproject.member.dto.serviceDto.ValidateEmailDto;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StopWatch;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CollaboRequestRepository collaboRequestRepository;

    @Mock
    private PostRepository postRepository;
    StopWatch stopwatch;
    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(collaboRequestRepository, postRepository);

    @BeforeEach
    void setData() {
        stopwatch = new StopWatch("API Controller Test");
        stopwatch.start();
    }

    @AfterEach
    void stop() {
        stopwatch.stop();
        System.out.println("===================================================");
        System.out.println("개별 테스트 성능 측정 결과");
        System.out.println("===================================================");
        System.out.println("Time Seconds = "+stopwatch.getTotalTimeSeconds()+"s");
        System.out.println("Time Millis = "+stopwatch.getTotalTimeMillis()+"ms");
        System.out.println("Time Nanos = "+stopwatch.getTotalTimeNanos()+"ns");
        System.out.println(stopwatch.prettyPrint());
        System.out.println("===================================================");
    }

    @Test
    @DisplayName("Email 검증 (1) 이메일 형식 미준수")
    void validateEmail_WRONG_FORMAT() {
        ValidateEmailDto validateEmailDto = ValidateEmailDto.builder()
                .email("com")
                .build();
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            memberService.validateEmail(validateEmailDto);
        });
        globalExceptionHandler.handleFormatException((FormatException) exception);
    }

    @Test
    @DisplayName("Email 검증 (2) 이메일 중복")
    void validateEmail_DUPLICATED_EMAIL() {
        ValidateEmailDto validateEmailDto = ValidateEmailDto.builder()
                .email("asdf@asdf.com")
                .build();
        Member member = Member.builder().build();
        when(memberRepository.findByEmail(validateEmailDto.getEmail())).thenReturn(Optional.ofNullable(member));

        Throwable exception = assertThrows(RuntimeException.class, () -> {
            memberService.validateEmail(validateEmailDto);
        });
        globalExceptionHandler.handleDuplicationException((DuplicationException) exception);
    }

    @Test
    void validateNickname() {
    }

    @Test
    void signUp() {
    }

    @Test
    void login() {
    }

    @Test
    void tokenReissuance() {
    }

    @Test
    void memberInfo() {
    }

    @Test
    void doFollow() {
    }

    @Test
    void doUnfollow() {
    }
}