package com.round2.round2.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="course_chapter")
public class CourseChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(columnDefinition = "TEXT", name = "introduction")
    private String introduction;
    @Column (name = "runtime")
    private double runtime;
    @Column (columnDefinition = "TEXT", name = "video_url")
    private String videoUrl;
    @Column (name = "created_at")
    private LocalDateTime createdAt;
    @Column (name = "deleted_at")
    private LocalDateTime deletedAt;

}
