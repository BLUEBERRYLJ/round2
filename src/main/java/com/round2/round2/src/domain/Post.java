package com.round2.round2.src.domain;

import com.round2.round2.src.post.model.CreatePostRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="post")

public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PostCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content; //글자수제한

    private String title;//글자수제한

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_anonymous")
    private Boolean isAnonymous;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();

    @Column(name = "view_count", columnDefinition = "integer default 0", nullable = false)
    private int viewCount;



    public static Post createPost (Member member, PostCategory category, CreatePostRequest req) {
        Post Post = new Post();
        Post.setMember(member);
        Post.setCategory(category);
        Post.setTitle(req.getTitle());
        Post.setContent(req.getContent());
        Post.createdAt = LocalDateTime.now();
        Post.setIsAnonymous(req.getIsAnonymous());
        Post.status = Status.ACTIVE;
        return Post;
    }

//    public void updatePost (UpdatePostRequest req){
//        this.setTitle(req.getTitle());
//        this.setContent(req.getContent());
//        this.updatedAt = LocalDateTime.now();
//        this.status = Status.ACTIVE.ACTIVE;
//    }



    public void deletePost (){
        this.deletedAt = LocalDateTime.now();
        this.status = Status.INACTIVE;
    }

    public void updateView() {
        if (viewCount == 0) {
            this.viewCount = 1;
        } else {
            this.viewCount = viewCount + 1;
        }
    }

}
