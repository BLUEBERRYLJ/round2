package com.round2.round2.src.course.model;

import lombok.Getter;

@Getter
public class ChapterInfoDto {
    private int chapterId;
    private String chapterName;
    private double runtime;

    public ChapterInfoDto(String chapterName, double runtime, int chapterId) {
        this.chapterName = chapterName;
        this.runtime = runtime;
        this.chapterId = chapterId;
    }
}
