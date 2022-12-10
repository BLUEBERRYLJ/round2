package com.round2.round2.src.comment;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.Comment;
import com.round2.round2.src.domain.Member;
import com.round2.round2.src.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.round2.round2.config.exception.ErrorCode.*;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;
    public Post findPostbyId(Long postId) throws CustomException{
        Post result = em.find(Post.class, postId);
        if (result == null) {
            throw new CustomException(POST_NOT_EXIST);
        }
        return result;
    }

    public Member findMemberbyId(Long id) {

        Member result = em.find(Member.class, id);
        if (result == null) {
            throw new CustomException(USER_NOT_FOUND);
        }
        return result;
    }

    public Comment findCommentById(Long id) {
        Comment result = em.find(Comment.class, id);
        if (result == null) {
            throw new CustomException(COMMENT_NOT_EXIST);
        }
        return result;
    }

    public Long findAnonymousId(Post post, Long memberIdByJwt ) throws CustomException {
        Long newAnonymousId;
        /**
         * case 1: 해당 게시글 커멘츠가 멤버가 있는지 없는지 확인하고 있으면 그 전 id 부여
         */
        List<Comment> CommentsByMember = em.createQuery("select tc from Comment tc join fetch tc.post as tp join fetch tc.member as m where tp.id = :postId and m.id = :memberId and tc.isAnonymous = true", Comment.class)
                .setParameter("postId", post.getId())
                .setParameter("memberId", memberIdByJwt)
                .getResultList();
        if (CommentsByMember.size() != 0) { //있으면 list 첫번째 element 반환. 중복은 없을테니
            return CommentsByMember.get(0).getAnonymousId();
        }

        /**
         * case 2: 댓글 단 이력이 없고 익명 댓글을 달고싶을때: anonymousId 부여받음
         */
        List<Comment> Comments = post.getCommentList();
        Comment CommentWithMaxAnonymousId;

        try {  //게시물에서 제일 큰 id를 찾은 후 +1 한 id 를 내 댓글에 새로운 anonymousId 로 부여
            // 닉네임으로 단 사람만 있을 경우에도 그냥 0+1 을 해줘서 1을 부여해줌
            CommentWithMaxAnonymousId = Comments.stream()
                    .max(Comparator.comparingLong(Comment::getAnonymousId))//nullPointerException
                    .get();
            newAnonymousId = CommentWithMaxAnonymousId.getAnonymousId() + 1;
            return newAnonymousId;

        } catch (NoSuchElementException e) {  //게시물에 기존 익명 id 가 아예 없을때: id 로 1 부여 --> 댓글이 아예 없을때
            newAnonymousId = Long.valueOf(1);
        }
        return newAnonymousId;
    }

    public void saveComment (Comment comment) {
        try {
            em.persist(comment);
        } catch (Exception e) {
            throw new CustomException(FAILED_TO_CREATECOMMENT);
        }

    }
}
