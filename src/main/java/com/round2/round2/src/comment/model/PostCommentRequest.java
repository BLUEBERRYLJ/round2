package com.round2.round2.src.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCommentRequest {
    private Long postId;
    private Long parentCommentId;
    private Long mentionId;
    private String content;
    private boolean isAnonymous;
}
