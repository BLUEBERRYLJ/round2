package com.round2.round2.src.post.model;

import com.round2.round2.src.domain.Post;
import lombok.Getter;

import static com.round2.round2.config.DateTimeConverter.convertLocaldatetimeToTime;

@Getter
public class HomeBestPostResponse {

    private Long postId;
    private String title;
    private String contents;
    private String nickname;
    private int likeCount;
    private int commentCount;
    private String createdAt;


    public HomeBestPostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContent();
        if (post.getIsAnonymous() == true) {
            this.nickname = "익명";
        } else {
            this.nickname = post.getMember().getName();
        }
        this.likeCount = post.getPostLikeList().size();
        this.commentCount = post.getCommentList().size();
        this.createdAt = convertLocaldatetimeToTime(post.getCreatedAt());
    }
}
