package com.round2.round2.src.course.model;

import com.round2.round2.src.domain.Course;
import lombok.Getter;

@Getter
public class CourseListResponse {

    private Long courseId;
    private String courseName;
    private String instructor;
    private int courseCount;
    private String imageUrl;

    public CourseListResponse(Course c) {
        this.courseId = c.getId();
        this.courseName = c.getCourseName();
        this.instructor = c.getInstructor();
        this.courseCount = c.getCourseChapterList().size();
        this.imageUrl = c.getIntroUrl();
    }
}
