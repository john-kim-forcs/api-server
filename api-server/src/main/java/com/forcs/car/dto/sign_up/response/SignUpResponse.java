package com.forcs.car.dto.sign_up.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import com.forcs.car.entity.Member;

public record SignUpResponse(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b7a-9b9b9b9b9b9b")
        UUID id,
        @Schema(description = "회원 아이디", example = "forcs001")
        String account,
        @Schema(description = "회원 이름", example = "홍길동")
        String name,
        @Schema(description = "회원 나이", example = "30")
        Integer age
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getAccount(),
                member.getName(),
                member.getAge()
        );
    }
}
