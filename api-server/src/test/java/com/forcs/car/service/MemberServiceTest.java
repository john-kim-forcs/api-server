package com.forcs.car.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.forcs.car.common.MemberType;
import com.forcs.car.dto.member.request.MemberUpdateRequest;
import com.forcs.car.dto.member.response.MemberDeleteResponse;
import com.forcs.car.dto.member.response.MemberInfoResponse;
import com.forcs.car.dto.member.response.MemberUpdateResponse;
import com.forcs.car.entity.Member;
import com.forcs.car.repository.MemberRepository;
import com.forcs.car.service.MemberService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Autowired
    MemberServiceTest(MemberService memberService, MemberRepository memberRepository, PasswordEncoder encoder) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    void get_member() {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .account("forcs001")
                .password("1234")
                .name("홍길동")
                .type(MemberType.USER)
                .build());
        // when
        MemberInfoResponse response = memberService.getMemberInfo(savedMember.getId());
        // then
        assertThat(response.id()).isEqualTo(savedMember.getId());
        assertThat(response.account()).isEqualTo("forcs001");
        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.type()).isEqualTo(MemberType.USER);
    }

    @Test
    void invalid_member() {
        // given
        // when
        // then
        assertThatThrownBy(() -> memberService.getMemberInfo(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    void delete_member() {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .account("forcs001")
                .password("1234")
                .build());
        // when
        MemberDeleteResponse result = memberService.deleteMember(savedMember.getId());
        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).isEmpty();
        assertThat(result.result()).isEqualTo(true);
    }

    @Test
    void update_member() {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .account("forcs001")
                .password(encoder.encode("1234"))
                .build());
        // when
        MemberUpdateRequest request = new MemberUpdateRequest("1234", "5678", "홍길동", 27);
        MemberUpdateResponse result = memberService.updateMember(savedMember.getId(), request);
        // then
        assertThat(result.result()).isEqualTo(true);
        assertThat(result.name()).isEqualTo("홍길동");
        assertThat(result.age()).isEqualTo(27);
        Member member = memberRepository.findAll().get(0);
        assertThat(encoder.matches("5678", member.getPassword())).isEqualTo(true);
    }
}