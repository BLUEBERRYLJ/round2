package com.round2.round2.src.member.model;

import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.MemberCourse;
import lombok.Getter;

@Getter
public class MyCurrentCourseDTO {

    private String courseName;
    private String progress;  // completedTime / runTime * 100

    public MyCurrentCourseDTO(MemberCourse memberCourse) {
        this.courseName = memberCourse.getCourse().getCourseName();
        double completedTime = memberCourse.getCompletedTime();
        double totalRuntime = memberCourse.getCourse().getRuntime();
        this.progress = ((int)((completedTime / totalRuntime) * 100)) + "%";
    }
}
