package com.round2.round2.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="course_category")
public class CourseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "courseCategory")
    private List<Course> courseList = new ArrayList<>();
}
