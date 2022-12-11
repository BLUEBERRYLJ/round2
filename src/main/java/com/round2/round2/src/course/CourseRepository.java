package com.round2.round2.src.course;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.CourseCategory;
import com.round2.round2.src.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

//import static com.round2.round2.config.exception.ErrorCode.EMPTY_COURSE_LIST;
//import static com.round2.round2.config.exception.ErrorCode.INVALID_COURSE_CATEGORY;

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
}
