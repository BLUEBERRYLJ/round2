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
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;

    @Column (name = "course_name")
    private String courseName;

    private String instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CourseCategory category;

    @Column (columnDefinition = "TEXT", name = "introduction")
    private String introduction;

    @Column(columnDefinition = "TEXT", name = "intro_url")
    private String introUrl;
    @Column (name = "runtime")
    private double runtime;
    @Column (name = "created_at")
    private LocalDateTime createdAt;
    @Column (name = "deleted_at")
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "course")
    private List<CourseChapter> courseChapterList = new ArrayList<>();
}
