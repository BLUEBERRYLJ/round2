package com.round2.round2.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment_like")

public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


//    public static CommentLike likesComment(Comment Comment, Member member) {
//
//        List<CommentLike> CommentLikes = Comment.getCommentLikeList();
//        for (CommentLike CommentLike : CommentLikes) {
//            if (CommentLike.getMember().equals(member)) {
//                return null;//중복.
//            }
//        }
//        CommentLike CommentLike = new CommentLike();
//        CommentLike.setMember(member);
//        CommentLike.setComment(Comment);
//        CommentLike.createdAt = LocalDateTime.now();
//
//        return CommentLike;
//    }


}
