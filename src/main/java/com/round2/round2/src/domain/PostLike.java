package com.round2.round2.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="post_like")

public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    public static PostLike likesPost(Post Post, Member member) {
//        List<PostLike> PostLikeList = member.getPostLikeList();
//        if (PostLikeList == null || PostLikeList.isEmpty()) {
//        }
//        else {
//            for (PostLike PostLike : PostLikeList) {
//                if (Objects.equals(PostLike.getPost().getId(), Post.getId())) {
//                    return null;
//                }
//            }
//        }
//        PostLike PostLike = new PostLike();
//        PostLike.setMember(member);
//        PostLike.setPost(Post);
//        PostLike.createdAt = LocalDateTime.now();
//        return PostLike;
//    }

}
