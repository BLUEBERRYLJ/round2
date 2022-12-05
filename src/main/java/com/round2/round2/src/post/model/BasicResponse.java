package com.round2.round2.src.post.model;

import lombok.Getter;

@Getter
public class BasicResponse {
    private String message;

    public BasicResponse(String message) {
        this.message = message;
    }
}
