package com.newnation.member.dto;

import com.newnation.member.entity.MemberRoleEnum;
import com.newnation.member.entity.ResponseMsg;
import lombok.Getter;

@Getter
public class LoginResponseDTO extends ResponseDTO {
    private String token;
    private MemberRoleEnum authType;

    public LoginResponseDTO(String token, String msg, MemberRoleEnum authType) {
        super(msg);
        this.token = token;
        this.authType = authType;
    }
}
