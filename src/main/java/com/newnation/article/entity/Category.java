package com.newnation.article.entity;

import lombok.Getter;

@Getter
public enum Category {
    SOCIETY("SOCIETY"),
    ENVIRONMENT("ENVIRONMENT"),
    TECH("TECH"),
    ETC("ETC");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
