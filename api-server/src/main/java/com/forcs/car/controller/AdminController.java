package com.forcs.car.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forcs.car.dto.ApiResponse;
import com.forcs.car.security.AdminAuthorize;
import com.forcs.car.service.AdminService;

@Tag(name = "Administration")
@RequiredArgsConstructor
@AdminAuthorize
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "회원 목록 조회")
    @GetMapping("/members")
    public ApiResponse getAllMembers() {
        return ApiResponse.success(adminService.getMembers());
    }

    @Operation(summary = "관리자 목록 조회")
    @GetMapping("/admins")
    public ApiResponse getAllAdmins() {
        return ApiResponse.success(adminService.getAdmins());
    }
}
