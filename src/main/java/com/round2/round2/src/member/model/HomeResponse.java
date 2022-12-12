package com.round2.round2.src.member.model;

import lombok.Getter;

import java.util.List;

@Getter
public class HomeResponse {

    private String username;
    private List<MyCurrentCourseDTO> myCurrentCourseDTOList;
    private String recommendName;
    private List<RecommendCourseDTO> recommendCourseDTOList;



}
