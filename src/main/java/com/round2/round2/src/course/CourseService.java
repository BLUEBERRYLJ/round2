package com.round2.round2.src.course;

import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CourseService {


    private final CourseRepository courseRepository;
    /**
     * 2.1 강의 리스트 API
     */
    public List<Course> findPostList(int category) {
        return courseRepository.findCourseList(category);
    }

}
