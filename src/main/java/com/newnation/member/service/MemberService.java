package com.newnation.member.service;

import com.newnation.global.jwt.JwtUtil;
import com.newnation.member.dto.LoginRequestDTO;
import com.newnation.member.dto.LoginResponseDTO;
import com.newnation.member.dto.ResponseDTO;
import com.newnation.member.dto.SignupRequestDTO;
import com.newnation.member.entity.Member;
import com.newnation.member.entity.MemberRoleEnum;
import com.newnation.member.entity.ResponseMsg;
import com.newnation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j(topic = "회원가입, 로그인 서비스 로직")
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN -> 환경변수 값으로 변경 예정
    private static final Map<String, MemberRoleEnum> tokenRoleMap = Map.of(
            "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC",
            MemberRoleEnum.ADMIN
    );

    // 비밀번호 검증 패턴
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_~]).{8,15}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public ResponseDTO signup(SignupRequestDTO requestDTO) {
        // 비밀번호 검증
        String password = requestDTO.getPassword();
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 영어 대소문자, 숫자, 특수문자를 포함한 8~15자리 입니다.");
        }

        // 비밀번호 암호화
        password = passwordEncoder.encode(requestDTO.getPassword());

        // email 중복 확인
        String email = requestDTO.getEmail();
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        // 관리자 여부 확인 및 권한 부여
        MemberRoleEnum role = determineRoleByToken(requestDTO.getAdminToken());

        Member member = new Member(requestDTO.getEmail(), password, requestDTO.getNickname(), role);

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

        return new LoginResponseDTO(token, ResponseMsg.LOGIN_SUCCESS.getMsg());
    }

    private MemberRoleEnum determineRoleByToken(String adminToken) {
        if (adminToken == null) {
            return MemberRoleEnum.USER;
        }
        MemberRoleEnum role = tokenRoleMap.get(adminToken);
        if (role == null) {
            throw new IllegalArgumentException("admin token이 맞지 않습니다.");
        }
        return role;
    }
}
