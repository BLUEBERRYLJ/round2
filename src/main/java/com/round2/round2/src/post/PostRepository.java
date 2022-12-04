package com.round2.round2.src.post;

import com.round2.round2.src.domain.Member;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.domain.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;


    /**
     * 게시물 카테고리 찾기
     */
    public PostCategory findCategoryById(int id) {
        return em.find(PostCategory.class, id);
    }


    /**
     * 게시물 저장
     */
    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }


    public Member findMemberById(Long id) {
        return em.find(Member.class, id);

    }
}
