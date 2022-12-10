package com.round2.round2.src.post;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.Member;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.domain.PostCategory;
import com.round2.round2.src.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.round2.round2.config.exception.ErrorCode.*;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Member findMemberById(Long id) {
        Member member = em.find(Member.class, id);
        if (member == null) {
            throw new CustomException(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 3.1 게시물 베스트
     */
    public List<Post> findBestPost() {
        List<Post> posts = em.createQuery(
"select p from Post p join fetch p.member m where p.status = :status and p.postLikeList.size > 5 order by p.createdAt desc ", Post.class)
        .setParameter("status", Status.ACTIVE)
        .setFirstResult(0)
        .setMaxResults(3)
        .getResultList();
        if (posts.size() == 0) {
            throw new CustomException(EMPTY_BEST_POSTS);
        }
        return posts;
    }

    /**
     * 게시물 카테고리 찾기
     */
    public PostCategory findCategoryById(int id) {
        PostCategory category = em.find(PostCategory.class, id);
        if (category == null) {
            throw new CustomException(INVALID_POST_CATEGORY);
        }
        return category;
    }

    /**
     * 게시물 저장
     */
    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }


}
