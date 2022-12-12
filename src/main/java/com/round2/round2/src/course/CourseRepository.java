package com.round2.round2.src.course;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.CourseChapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.round2.round2.config.exception.ErrorCode.*;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;

    /**
     * 2.2 강의 상세 및 강의 챕터 리스트 리턴 api
     */
    public Course findCourseById(Long courseId) {
        Course course = em.find(Course.class, courseId);
        if (course == null) {
            throw new CustomException(COURSE_NOT_FOUND);
        }
        return course;
    }

    /**
     * 2.3 강의 챕터 상세 api
     */
    public CourseChapter findChapterById(int chapterId) {
        CourseChapter chapter = em.find(CourseChapter.class, chapterId);
        if (chapter == null) {
            throw new CustomException(COURSE_NOT_FOUND);
        }
        return chapter;
    }
}
