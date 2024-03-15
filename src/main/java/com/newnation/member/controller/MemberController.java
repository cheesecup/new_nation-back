package com.newnation.member.controller;

import com.newnation.member.dto.*;
import com.newnation.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "회원가입, 로그인")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(
            @Valid @RequestBody SignupRequestDTO requestDTO
    ) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(memberService.signup(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.login(requestDTO));
    }

    // 유저 인증 확인
    @GetMapping
    public ResponseEntity<RoleResponseDTO> checkMemberRole(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.checkMemberRole(userDetails));
    }
}
