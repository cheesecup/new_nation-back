package com.newnation.member.entity;

import lombok.Getter;

@Getter
public enum ResponseMsg {
    SIGNUP_SUCCESS("회원가입 성공"),
    LOGIN_SUCCESS("로그인 성공");

    private final String msg;

    ResponseMsg(String msg) {
        this.msg = msg;
    }
}
