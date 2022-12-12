package com.round2.round2.src.course.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CourseDetailResponse {
    private Long courseId;
    private String courseName;
    private String introduction;
    private double runtime;
    private List<ChapterInfoDto> chapterInfoDtoList;

    public CourseDetailResponse(String courseName, String introduction, double runtime, List<ChapterInfoDto> chapterInfoDtoList, Long courseId) {
        this.courseName = courseName;
        this.introduction = introduction;
        this.runtime = runtime;
        this.chapterInfoDtoList = chapterInfoDtoList;
        this.courseId = courseId;
    }
}
