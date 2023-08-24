package com.forcs.car.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forcs.car.dto.ApiResponse;
import com.forcs.car.dto.sign_in.request.SignInRequest;
import com.forcs.car.dto.sign_up.request.SignUpRequest;
import com.forcs.car.service.SignService;

@Tag(name = "Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthenticationController {
    private final SignService signService;

    @Operation(summary = "Sign Up")
    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        return ApiResponse.success(signService.registMember(request));
    }

    @Operation(summary = "Sign In")
    @PostMapping("/sign-in")
    public ApiResponse signIn(@RequestBody SignInRequest request) {
        return ApiResponse.success(signService.signIn(request));
    }
}
