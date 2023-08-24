package com.forcs.car.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.forcs.car.common.MemberType;
import com.forcs.car.dto.member.response.MemberInfoResponse;
import com.forcs.car.entity.Member;
import com.forcs.car.repository.MemberRepository;
import com.forcs.car.service.AdminService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdminServiceTest {
    private final AdminService adminService;
    private final MemberRepository memberRepository;

    @Autowired
    AdminServiceTest(AdminService adminService, MemberRepository memberRepository) {
        this.adminService = adminService;
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    void admin_all_memberinfo() {
        // given
        memberRepository.save(Member.builder()
                .account("forcs001")
                .password("1234")
                .name("홍길동")
                .type(MemberType.USER)
                .build());
        memberRepository.save(Member.builder()
                .account("forcs002")
                .password("1234")
                .name("전우치")
                .type(MemberType.USER)
                .build());
        memberRepository.save(Member.builder()
                .account("forcs003")
                .password("1234")
                .name("김삿갓")
                .type(MemberType.USER)
                .build());
        // when
        List<MemberInfoResponse> members = adminService.getMembers();
        // then
        assertThat(members).hasSize(3);
        assertThat(members.get(0).account()).isEqualTo("forcs001");
        assertThat(members.get(0).name()).isEqualTo("홍길동");
        assertThat(members.get(0).type()).isEqualTo(MemberType.USER);
        assertThat(members.get(1).account()).isEqualTo("forcs002");
        assertThat(members.get(1).name()).isEqualTo("전우치");
        assertThat(members.get(1).type()).isEqualTo(MemberType.USER);
        assertThat(members.get(2).account()).isEqualTo("forcs003");
        assertThat(members.get(2).name()).isEqualTo("김삿갓");
        assertThat(members.get(2).type()).isEqualTo(MemberType.USER);
    }

    @Test
    void admin_all_admininfo() {
        // given
        memberRepository.save(Member.builder()
                .account("forcs001")
                .password("1234")
                .name("홍길동")
                .type(MemberType.ADMIN)
                .build());
        memberRepository.save(Member.builder()
                .account("forcs002")
                .password("1234")
                .name("전우치")
                .type(MemberType.ADMIN)
                .build());
        memberRepository.save(Member.builder()
                .account("forcs003")
                .password("1234")
                .name("김삿갓")
                .type(MemberType.ADMIN)
                .build());
        // when
        List<MemberInfoResponse> admins = adminService.getAdmins();
        // then
        assertThat(admins).hasSize(3);
        assertThat(admins.get(0).account()).isEqualTo("forcs001");
        assertThat(admins.get(0).name()).isEqualTo("홍길동");
        assertThat(admins.get(0).type()).isEqualTo(MemberType.ADMIN);
        assertThat(admins.get(1).account()).isEqualTo("forcs002");
        assertThat(admins.get(1).name()).isEqualTo("전우치");
        assertThat(admins.get(1).type()).isEqualTo(MemberType.ADMIN);
        assertThat(admins.get(2).account()).isEqualTo("forcs003");
        assertThat(admins.get(2).name()).isEqualTo("김삿갓");
        assertThat(admins.get(2).type()).isEqualTo(MemberType.ADMIN);
    }
}