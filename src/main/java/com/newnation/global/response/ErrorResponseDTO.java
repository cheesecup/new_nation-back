package com.newnation.global.response;

import lombok.Getter;

@Getter
public class ErrorResponseDTO<T> {
    private int statusCode;
    private String msg;
    private T data;

    public ErrorResponseDTO(int statusCode, String msg, T data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }
}