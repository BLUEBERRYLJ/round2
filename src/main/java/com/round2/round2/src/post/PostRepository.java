package com.round2.round2.src.post;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.*;
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
        return member;
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
        if (posts.size() == 0)
            throw new CustomException(EMPTY_BEST_POSTS);
        return posts;
    }

    /**
     * 게시물 카테고리 찾기
     */
    public PostCategory findCategoryById(int id) {
        PostCategory category = em.find(PostCategory.class, id);
        if (category == null)
            throw new CustomException(INVALID_POST_CATEGORY);
        return category;
    }

    /**
     * 3.2 게시물 리스트
     */
    public List<Post> findPostList(int category) {
        List<Post> postList = em.createQuery("select p from Post p join p.category as c join fetch p.member as m where p.status = :status and c.categoryId = :categoryId order by p.createdAt desc ", Post.class)
                .setParameter("status", Status.ACTIVE)
                .setParameter("categoryId", category)
                .getResultList();
        if (postList.size() == 0)
            throw new CustomException(EMPTY_POST_LIST);
        return postList;
    }

    /**
     * 3.3 게시물 저장
     */
    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    /**
     * 3.4 게시물 상세
     */
//    public Post findPostById(Long postId) {
//
//    }

    /**
     * 3.5 게시물 삭제
     */
    public Member findMemberbyId(Long id) {

        Member result = em.find(Member.class, id);
        if (result == null) {
            throw new CustomException(USER_NOT_FOUND);
        }
        return result;
    }

    public Post findPostbyId(Long postId) throws CustomException{
        Post result = em.find(Post.class, postId);
        if (result == null) {
            throw new CustomException(POST_NOT_EXIST);
        }
        return result;
    }

    public List<Comment> findAllComment(Long postId) {
        List<Comment> allTotalComments = em.createQuery("select c from Comment c where c.post.id = :id", Comment.class)
                .setParameter("id", postId)
                .getResultList();
        return allTotalComments;
    }



//    public Post findPostById(Long postId) {
//        Post post = em.find(Post.class, postId);
//        if (post == null)
//            throw new CustomException(POST_NOT_EXIST);
//        return post;
//    }
    public boolean checkIsLiked(Long postId, Long memberId) {
        List<PostLike> postLikeList = em.createQuery("select pl from PostLike pl join pl.post p join pl.member m where p.id = :postId and m.id = :memberId", PostLike.class)
                .setParameter("postId", postId)
                .setParameter("memberId", memberId)
                .getResultList();
        if (postLikeList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 3.5 게시물 상세 댓글
     */
    public List<Comment> getComments(Long postId) {
        return em.createQuery("select c from Comment c join c.post as p where p.id = :id and c.parentCommentId = :parentId order by c.createdAt asc", Comment.class)
                .setParameter("id", postId)
                .setParameter("parentId", 0L)
                .getResultList();
    }
    /**
     * 게시물 상세 대댓글
     */
    public List<Comment> getCoComments(Long postId) {
        return em.createQuery("select c from Comment c join c.post as p where p.id = :id and c.parentCommentId <> :parentId order by c.createdAt asc", Comment.class)
                .setParameter("id", postId)
                .setParameter("parentId", 0L)
                .getResultList();
    }
    public Comment findCommentById(Long mentionId) {
            return em.find(Comment.class, mentionId);
    }
}
