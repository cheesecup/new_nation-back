package com.newnation.global.response;

import lombok.Getter;

@Getter
public class SuccessResponseDTO<T> {
    private int statusCode = 200;
    private String message;
    private T data;

    public SuccessResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
