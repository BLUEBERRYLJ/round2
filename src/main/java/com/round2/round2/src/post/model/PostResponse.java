package com.round2.round2.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class PostResponse extends BasicResponse{
    private Long postId;

//    public PostResponse(Long postId) {
//        this.postId = postId;
//    }

    public PostResponse(Long postId, String message) {
        super(message);
        this.postId = postId;
    }
}
