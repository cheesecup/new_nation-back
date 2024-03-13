package com.newnation.member.controller;

import com.newnation.member.dto.LoginRequestDTO;
import com.newnation.member.dto.LoginResponseDTO;
import com.newnation.member.dto.ResponseDTO;
import com.newnation.member.dto.SignupRequestDTO;
import com.newnation.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
