package com.round2.round2.src.course;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.CourseCategory;
import com.round2.round2.src.domain.CourseChapter;
import com.round2.round2.src.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.round2.round2.config.exception.ErrorCode.*;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;

    public List<Course> findCourseList(int categoryId) {
        List<Course> CourseList = em.createQuery("select c from Course c where c.category.id = :categoryId order by c.createdAt desc ", Course.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
//        if (CourseList.size() == 0)
//            throw new CustomException(EMPTY_COURSE_LIST);
        return CourseList;
    }

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
