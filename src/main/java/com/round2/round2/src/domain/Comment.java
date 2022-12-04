package com.round2.round2.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment")

public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private String content;


    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "mention_id")
    private Long mentionId;

    @Column(name = "anonymous_id")
    private Long anonymousId;

    @Column(name = "is_anonymous")
    private boolean isAnonymous;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;



//    public void modifyInactiveStatus() {
//        this.status = Status.INACTIVE; //update 문으로 변경
//    }

//    public static Comment createComment(Post Post, Member member, String content, Long parentCommentId, Long mentionId, boolean isAnonymous, Long anonymousId) {
//        Comment Comment = new Comment();
//        Comment.setPost(Post);
//        Comment.setMember(member);
//        Comment.setContent(content);
//        Comment.setParentCommentId(parentCommentId);
//        Comment.setMentionId(mentionId);
//        Comment.setAnonymous(isAnonymous);
//        Comment.setAnonymousId(anonymousId);
//        Comment.createdAt = LocalDateTime.now();
//        Comment.updatedAt = LocalDateTime.now();
//        Comment.status = Status.ACTIVE;
//
//        return Comment;
//    }
//
//    public void deleteComment (){
//        this.deletedAt = LocalDateTime.now();
//        this.status = Status.INACTIVE;
//    }

}
