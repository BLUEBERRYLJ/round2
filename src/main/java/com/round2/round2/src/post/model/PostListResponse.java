package com.round2.round2.src.post.model;

import com.round2.round2.src.domain.Post;
import lombok.Getter;

import static com.round2.round2.config.DateTimeConverter.convertLocaldatetimeToTime;

@Getter
public class PostListResponse {
    
    private Long postId;
    private String title;
    private String contents;
    private String nickname;
    private int likeCount;
    private int commentCount;
    private String createdAt;


    public PostListResponse(Post Post) {
        this.postId = Post.getId();
        this.title = Post.getTitle();
        this.contents = Post.getContent();
        if (Post.getIsAnonymous() == true) {
            this.nickname = "익명";
        } else {
            this.nickname = Post.getMember().getName();
        }
        this.likeCount = Post.getPostLikeList().size();
        this.commentCount = Post.getCommentList().size();
        this.createdAt = convertLocaldatetimeToTime(Post.getCreatedAt());
    }

}
