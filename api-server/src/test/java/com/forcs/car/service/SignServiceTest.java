package com.forcs.car.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.forcs.car.common.MemberType;
import com.forcs.car.dto.sign_in.request.SignInRequest;
import com.forcs.car.dto.sign_in.response.SignInResponse;
import com.forcs.car.dto.sign_up.request.SignUpRequest;
import com.forcs.car.dto.sign_up.response.SignUpResponse;
import com.forcs.car.entity.Member;
import com.forcs.car.repository.MemberRepository;
import com.forcs.car.service.SignService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SignServiceTest {
    private final SignService signService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Autowired
    SignServiceTest(SignService signService, MemberRepository memberRepository, PasswordEncoder encoder) {
        this.signService = signService;
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }
    
    @Test
    void signup() {
        // given
        SignUpRequest request = new SignUpRequest("forcs001", "1234", "홍길동", 30);
        // when
        SignUpResponse response = signService.registMember(request);
        // then
        assertThat(response.account()).isEqualTo("forcs001");
        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.age()).isEqualTo(30);
    }

    @Test
    void duplicated_id() {
        // given
        memberRepository.save(Member.builder()
                .account("forcs001")
                .password("1234")
                .build());
        SignUpRequest request = new SignUpRequest("forcs001", "1234", null, null);
        // when
        // then
        Assertions.assertThatThrownBy(() -> signService.registMember(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @Test
    void signin() {
        // given
        memberRepository.save(Member.builder()
                .account("forcs001")
                .password(encoder.encode("1234"))
                .name("홍길동")
                .type(MemberType.USER)
                .build());
        // when
        SignInResponse response = signService.signIn(new SignInRequest("forcs001", "1234"));
        // then
        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.type()).isEqualTo(MemberType.USER);
    }

    @Test
    void fail_to_signin() {
        // given
        memberRepository.save(Member.builder()
                .account("forcs001")
                .password("1234")
                .build());
        // when
        // then
        Assertions.assertThatThrownBy(() -> signService.signIn(new SignInRequest("forcs001", "12345")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}