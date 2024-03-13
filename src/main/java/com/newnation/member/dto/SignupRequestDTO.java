package com.newnation.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String nickname;
    private String adminToken;
}
