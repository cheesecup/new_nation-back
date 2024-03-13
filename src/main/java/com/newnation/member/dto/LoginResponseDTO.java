package com.newnation.member.dto;

import com.newnation.member.entity.ResponseMsg;
import lombok.Getter;

@Getter
public class LoginResponseDTO extends ResponseDTO {
    private String token;

    public LoginResponseDTO(String token, String msg) {
        super(msg);
        this.token = token;
    }
}
