package com.round2.round2.src.post.model;

import com.round2.round2.src.domain.Comment;
import com.round2.round2.src.domain.CommentLike;
import com.round2.round2.src.domain.Status;
import lombok.Getter;

import java.util.Objects;

import static com.round2.round2.config.DateTimeConverter.convertToDateAndTime;

@Getter
public class CoCommentDTO {

    private Long commentId;
    private Long parentCommentId;
    private String mention;
    private String name;
    private String content;
    private int likeCount;
    private boolean isLiked;
    private boolean isMyComment;

    private String createdAt;

    public CoCommentDTO(Comment coComment, Comment mention, Long memberId, Long authorId) {
        Long coCommentWriter = coComment.getMember().getId();

        this.commentId = coComment.getId();
        this.parentCommentId = coComment.getParentCommentId();

        if (coComment.isAnonymous() == true) {
            this.name = "익명 "+coComment.getAnonymousId();
        } else{
            this.name = coComment.getMember().getName();
        }

        if (mention.isAnonymous() == true) {
            this.mention = "익명 "+mention.getAnonymousId();
        } else {
            this.mention = mention.getMember().getName();

        }if (coComment.getStatus() == Status.INACTIVE) {
            this.content = "삭제된 댓글입니다.";
            name = "(비공개됨)";
        } else {
            this.content = coComment.getContent();
        }

        this.likeCount = coComment.getCommentLikeList().size();


        for (CommentLike cl : coComment.getCommentLikeList()) {
            if (cl.getMember().getId() == memberId) {
                this.isLiked = true;
                break;
            } else {
                this.isLiked = false;
            }
        }
        if (Objects.equals(coCommentWriter, memberId)) {
            isMyComment = true;
        }
        this.createdAt = convertToDateAndTime(coComment.getCreatedAt());
    }
}
