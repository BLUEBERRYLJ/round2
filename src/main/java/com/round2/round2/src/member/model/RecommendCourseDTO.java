package com.round2.round2.src.member.model;

import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.MemberRecommend;
import lombok.Getter;

@Getter
public class RecommendCourseDTO {

    private Long courseId;
    private String courseName;
    private String courseDescription;



    public RecommendCourseDTO(MemberRecommend rc) {
        Course course = rc.getCourse();
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getIntroduction();
    }

    public RecommendCourseDTO(Course course) {
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getIntroduction();
    }
}
