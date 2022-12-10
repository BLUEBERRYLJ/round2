package com.round2.round2.src.post.model;
import com.round2.round2.src.domain.Post;
import lombok.Getter;

import static com.round2.round2.config.DateTimeConverter.convertToDateAndTime;

@Getter
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String nickname;
    private int likeCount;
    private int commentCount;
    private boolean isMypost;
    private boolean isLiked;
    private String createdAt;
    private final int viewCount;
    
    public PostResponse(Post post, boolean isMypost, boolean isLiked) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        if (post.getIsAnonymous() == true) {
            this.nickname = "익명";
        } else {
            this.nickname = post.getMember().getName();
        }
        this.likeCount = post.getPostLikeList().size();
        this.commentCount = post.getCommentList().size();
        this.isMypost = isMypost;
        this.isLiked = isLiked;
        this.createdAt = convertToDateAndTime(post.getCreatedAt());
        this.viewCount = post.getViewCount();
    }

}
