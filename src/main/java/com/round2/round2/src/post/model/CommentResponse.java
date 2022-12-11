package com.round2.round2.src.post.model;

import com.round2.round2.src.domain.Comment;
import com.round2.round2.src.domain.CommentLike;
import com.round2.round2.src.domain.Status;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

import static com.round2.round2.config.DateTimeConverter.convertToDateAndTime;

@Getter
public class CommentResponse {

    private Long commentId;
    private String name;
    private String content;
    private int likeCount;
    private boolean isLiked;
    private boolean isMycomment;
    private String createdAt;
    private List<CoCommentDTO> coCommentList;



    public CommentResponse(Comment comment, List<CoCommentDTO> coCommentDTOList, Long memberId, Long authorId) {
        commentId = comment.getId();
        Long commentWriter = comment.getMember().getId();

        if (comment.isAnonymous() == true) {
            name = "익명 "+comment.getAnonymousId();
        } else {
            name = comment.getMember().getName();
        }
        if (comment.getStatus() == Status.INACTIVE) {
            content = "삭제된 댓글입니다.";
            name = "(비공개됨)";
        } else {
            content = comment.getContent();
        }
        likeCount = comment.getCommentLikeList().size();

        for (CommentLike cl : comment.getCommentLikeList()) {
            if (cl.getMember().getId() == memberId) {
                isLiked = true;
                break;
            } else {
                isLiked = false;
            }
        }

        if (Objects.equals(commentWriter, memberId)) {
            isMycomment = true;
        }

        createdAt = convertToDateAndTime(comment.getCreatedAt());
        coCommentList = coCommentDTOList;
    }


}
