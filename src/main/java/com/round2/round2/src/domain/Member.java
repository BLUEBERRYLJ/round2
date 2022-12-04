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
@Table(name="member")

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email; //regex 추가
    private String pwd; //regex 추가

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;
    private String role;


    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Course> courseList = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


    //== 생성 메서드 ==// -> constructor 역할.
    public static Member createMember(String name, String email, String pwd) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPwd(pwd);
        member.createdAt = LocalDateTime.now();
        member.updatedAt = LocalDateTime.now();
        member.status = Status.ACTIVE;
        member.role = "UNSUBSCRIBED";
        return member;
    }


    public void deleteMember() {
        this.deletedAt = LocalDateTime.now();
        this.status = Status.INACTIVE;

    }
}



