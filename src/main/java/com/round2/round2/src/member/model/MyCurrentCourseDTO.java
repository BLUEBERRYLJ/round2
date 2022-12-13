package com.round2.round2.src.member.model;

import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.MemberCourse;
import lombok.Getter;

@Getter
public class MyCurrentCourseDTO {

    private String courseName;
    private String progress;  // completedTime / runTime * 100
    private String color;

    public MyCurrentCourseDTO(MemberCourse memberCourse) {
        Course myCourse = memberCourse.getCourse();
        this.courseName = myCourse.getCourseName();
        double completedTime = memberCourse.getCompletedTime();
        double totalRuntime = myCourse.getRuntime();
        this.progress = ((int)((completedTime / totalRuntime) * 100)) + "%";
        this.color = memberCourse.getCourse().getColor();
    }
}
