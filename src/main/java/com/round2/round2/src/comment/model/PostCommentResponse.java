package com.round2.round2.src.comment.model;

import com.round2.round2.src.domain.Comment;
import lombok.Getter;

import java.util.Objects;

import static com.round2.round2.config.DateTimeConverter.convertToDateAndTime;

@Getter
public class PostCommentResponse {
    Long commentId;
    String nickname;
    String createdAt;
    private boolean iscommentFromAuthor;

    public PostCommentResponse(Long anonymousId, Comment comment, Long authorId) {
        Long commentWriter = comment.getMember().getId();
        this.commentId = comment.getId();
        if (comment.isAnonymous() == false) {
            this.nickname = comment.getMember().getName();
        } else if (comment.isAnonymous() && anonymousId != 0){
            this.nickname = "익명 " + anonymousId;
        } else if (comment.isAnonymous() == false && comment.getMember().getId() == authorId) {
            this.nickname = comment.getMember().getName() + "(글쓴이)";
        } else {
            this.nickname = "익명(글쓴이)";
        }

        if (Objects.equals(commentWriter, authorId)) {
            iscommentFromAuthor = true;
        } else {
            iscommentFromAuthor = false;
        }
        createdAt = convertToDateAndTime(comment.getCreatedAt());


    }
}