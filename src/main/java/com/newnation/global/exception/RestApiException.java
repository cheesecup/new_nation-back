package com.newnation.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {
    private String msg;
    private int statusCode;

}
