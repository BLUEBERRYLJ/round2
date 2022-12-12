package com.round2.round2.src.course.model;

import lombok.Getter;

import java.util.List;

@Getter
public class ChapterDetailResponse {
    private String chapterName;
    private String videoUrl;
//    private List<ChapterInfoDto> chapterInfoDtoList;

    public ChapterDetailResponse(String chapterName, String videoUrl) {
        this.chapterName = chapterName;
        this.videoUrl = videoUrl;
//        this.chapterInfoDtoList = chapterInfoDtoList;
    }
}
