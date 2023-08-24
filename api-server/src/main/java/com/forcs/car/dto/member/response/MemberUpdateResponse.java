package com.forcs.car.dto.member.response;

import com.forcs.car.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateResponse(
        @Schema(description = "회원 정보 수정 성공 여부", example = "true")
        boolean result,
        @Schema(description = "회원 이름", example = "홍길동")
        String name,
        @Schema(description = "회원 나이", example = "30")
        Integer age
) {
    public static MemberUpdateResponse of(boolean result, Member member) {
        return new MemberUpdateResponse(result, member.getName(), member.getAge());
    }
}
