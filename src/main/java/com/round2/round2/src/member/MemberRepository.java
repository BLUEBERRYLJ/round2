package com.round2.round2.src.member;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.round2.round2.config.exception.ErrorCode.USER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;


    /**
     * 회원가입
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }


    public Member findMemberByEmail(String email) {
        List<Member> m = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        if (m.size() != 0) { //있으면 list 첫번째 element 반환. 중복은 없을테니
            return m.get(0);
        } else { //없으면 null 반환
            return null;
        }
    }

    public Member findMemberById(Long id) {
        Member result = em.find(Member.class, id);
        if (result == null) {
            throw new CustomException(USER_NOT_FOUND);
        }
        return result;
    }

    public List<Course> getCourseList() {
        List<Course> result = em.createQuery("select c from Course c where c.id < 4", Course.class)
                .getResultList();
        return  result;
    }
}
