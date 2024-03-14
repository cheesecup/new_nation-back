package com.newnation.global.response;

import lombok.Getter;

@Getter
public class SuccessResponseDTO<T> {
    private int statusCode = 200;
    private String msg;
    private T data;

    public SuccessResponseDTO(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
}
