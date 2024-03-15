package com.newnation.member.service;

import com.newnation.global.exception.NotFoundException;
import com.newnation.global.jwt.JwtUtil;
import com.newnation.member.dto.*;
import com.newnation.member.entity.Member;
import com.newnation.member.entity.MemberRoleEnum;
import com.newnation.member.entity.ResponseMsg;
import com.newnation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j(topic = "회원가입, 로그인 서비스 로직")
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseDTO signup(SignupRequestDTO requestDTO) {
        // 비밀번호 암호화
        String password = passwordEncoder.encode(requestDTO.getPassword());

        // email 중복 확인
        String email = requestDTO.getEmail();
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        Member member = new Member(requestDTO.getEmail(), password, requestDTO.getNickname(), MemberRoleEnum.USER);

        memberRepository.save(member);

        return new ResponseDTO(ResponseMsg.SIGNUP_SUCCESS.getMsg());
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(member.getEmail(), member.getRole());

        return new LoginResponseDTO(token, ResponseMsg.LOGIN_SUCCESS.getMsg(), member.getRole());
    }

    public RoleResponseDTO checkMemberRole(UserDetails userDetails) {
        // 사용자 정보 조회
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        return new RoleResponseDTO(member.getRole());
    }
}
