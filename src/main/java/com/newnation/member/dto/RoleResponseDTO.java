package com.newnation.member.dto;

import com.newnation.member.entity.MemberRoleEnum;
import lombok.Getter;

@Getter
public class RoleResponseDTO {
    private MemberRoleEnum authType;

    public RoleResponseDTO(MemberRoleEnum authType) {
        this.authType = authType;
    }
}
