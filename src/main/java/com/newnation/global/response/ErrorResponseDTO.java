package com.newnation.global.response;

import lombok.Getter;

@Getter
public class ErrorResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;

    public ErrorResponseDTO(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}