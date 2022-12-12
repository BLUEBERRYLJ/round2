package com.round2.round2.src.member.model;

import lombok.Getter;

import java.util.List;

@Getter
public class HomeResponse {

    private String username;
    private List<MyCurrentCourseDTO> myCurrentCourseDTOList;
    private String recommendName;
    private List<RecommendCourseDTO> recommendCourseDTOList;


//    public HomeResponse() {
//        username = "안녕하세요, 방문자님.";
//        myCurrentCourseDTOList =
//
//    }
    public HomeResponse(String username, List<MyCurrentCourseDTO> myCurrentCourseDTOList, List<RecommendCourseDTO> recommendCourseDTOList) {
        this.username =  username;
        this.myCurrentCourseDTOList = myCurrentCourseDTOList;
        this.recommendName = username;
        this.recommendCourseDTOList = recommendCourseDTOList;
    }
}
