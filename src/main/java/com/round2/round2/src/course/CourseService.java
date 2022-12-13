package com.round2.round2.src.course;

import com.round2.round2.src.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.course.model.ChapterDetailResponse;
import com.round2.round2.src.course.model.ChapterInfoDto;
import com.round2.round2.src.course.model.CourseDetailResponse;
import com.round2.round2.src.domain.CourseChapter;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {


    private final CourseRepository courseRepository;



    /**
     * 2.1 강의 리스트 API
     */
    public List<Course> findPostList(int category) {
        return courseRepository.findCourseList(category);
    }


    /**
     * 2.2 강의 상세 및 강의 챕터 리스트 리턴 api
     */
    @Transactional
    public CourseDetailResponse getCourseDetail(Long courseId) {
        Course course = courseRepository.findCourseById(courseId);
        List<ChapterInfoDto> chapterInfoDtoList = course.getCourseChapterList()
                .stream()
                .map(chapter -> new ChapterInfoDto(chapter.getChapterName(), chapter.getRuntime(), chapter.getId()))
                .collect(Collectors.toList());
        return new CourseDetailResponse(course.getCourseName(), course.getIntroduction(), course.getRuntime(), chapterInfoDtoList, course.getId());
    }

    /**
     * 2.3 강의 챕터 상세 api
     */
    public ChapterDetailResponse getChapterDetail(int chapterId) throws CustomException {
//        Course course = courseRepository.findCourseById(courseId);
        CourseChapter targetChapter = courseRepository.findChapterById(chapterId);

//        List<ChapterInfoDto> chapterInfoDtoList = course.getCourseChapterList()
//                .stream()
//                .map(chapter -> new ChapterInfoDto(chapter.getChapterName(), chapter.getRuntime()))
//                .collect(Collectors.toList());
        return new ChapterDetailResponse(targetChapter.getChapterName(), targetChapter.getVideoUrl());

    }
}
