package com.newnation.member.dto;

import com.newnation.member.entity.ResponseMsg;
import lombok.Getter;

@Getter
public class ResponseDTO {
    private String msg;

    public ResponseDTO(String msg) {
        this.msg = msg;
    }
}
