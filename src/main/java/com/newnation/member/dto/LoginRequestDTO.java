package com.newnation.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
