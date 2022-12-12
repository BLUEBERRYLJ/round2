package com.round2.round2.src.member.model;

import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.MemberCourse;
import lombok.Getter;

@Getter
public class MyCurrentCourseDTO {

    private String courseName;
    private String progress;  // completedTime / runTime * 100

    public MyCurrentCourseDTO(Course course, MemberCourse memberCourse) {
        this.courseName = course.getCourseName();
        double completedTime = memberCourse.getCompletedTime();
        double totalRuntime = course.getRuntime();
        this.progress = String.valueOf((completedTime / totalRuntime) * 100);
    }
}
