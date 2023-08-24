package com.forcs.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forcs.car.common.MemberType;
import com.forcs.car.dto.member.response.MemberInfoResponse;
import com.forcs.car.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getMembers() {
        return memberRepository.findAllByType(MemberType.USER).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getAdmins() {
        return memberRepository.findAllByType(MemberType.ADMIN).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }
}
