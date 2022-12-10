package com.round2.round2.src.comment;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.comment.model.PostCommentRequest;
import com.round2.round2.src.comment.model.PostCommentResponse;
import com.round2.round2.src.domain.Comment;
import com.round2.round2.src.domain.Member;
import com.round2.round2.src.domain.Post;
import com.round2.round2.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.round2.round2.config.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final JwtService jwtService;
    private final CommentRepository commentRepository;

    /**
     * 4.1 전체게시판 댓글 작성 api
     * @return commentId
     */
    @Transactional

    public PostCommentResponse createComment(PostCommentRequest postCommentRequest) throws CustomException {
        Long memberIdByJwt = jwtService.getUserIdx();
        Post post = commentRepository.findPostbyId(postCommentRequest.getPostId());
        if (post == null) {
            throw new CustomException(POST_NOT_EXIST);
        }


        /*
        1. 댓글을 달때: parent = null, mention = null.
        2. 처음 대댓글 달때 (b) : parent = a , mention = a.
        3. 대댓글에 대댓글 달때 (c) : parent = a, mention = b.

        에러 날 케이스
        1. 대댓글 달때: parent = null, mention = a.
        2. 대댓글 달때: parent = a, mention = null.
        3. 대댓글 달때: parent = 없는 id, mention = 없는 id.
        4. 대댓글 달떄: parent나 mention이 이게시물에 달린 댓글이 아닐때.

        -> 앱으로 통하는 통신만 가능하도록..?
        -> 여태껏 디비에 잘못들어간 에러들은 다 핸들링 해야할수도
         */

        // 잘못된 parentComment / mention Id
        List<Comment> PostComments = post.getCommentList();
        boolean parentFlag = false;
        boolean mentionFlag = false;

        if (postCommentRequest.getMentionId() == null && postCommentRequest.getParentCommentId() == null) {
        }
        else {
            for (Comment PostComment : PostComments) {
                if (Objects.equals(PostComment.getId(), postCommentRequest.getParentCommentId())) {
                    parentFlag = true;
                }
                if (Objects.equals(PostComment.getId(), postCommentRequest.getMentionId())) {
                    mentionFlag = true;
                }
                if (parentFlag == true && mentionFlag == true) {
                    break;
                }
            }
            if (parentFlag == false || mentionFlag == false) {
                throw new CustomException(FAILED_TO_CREATECOMMENT);
            }
        }


        Member member = commentRepository.findMemberbyId(memberIdByJwt);
        Long anonymousId;
        if (Objects.equals(member.getId(), post.getMember().getId())) { //댓쓴이가 author 일때
            anonymousId = Long.valueOf(0); //isAnonymous = true, but AnonymousNo is 0
        } else if (postCommentRequest.isAnonymous() == true) {
            anonymousId = commentRepository.findAnonymousId(post, memberIdByJwt);
        } else {
            anonymousId = Long.valueOf(0); // null -> 0 으로 수정
        }
        //댓글 생성
        Comment comment = Comment.createComment(post, member, postCommentRequest.getContent(), postCommentRequest.getParentCommentId(), postCommentRequest.getMentionId(), postCommentRequest.isAnonymous(), anonymousId);
        commentRepository.saveComment(comment);
        PostCommentResponse postCommentResponse = new PostCommentResponse(anonymousId, comment, post.getMember().getId());
        return postCommentResponse;

    }

    @Transactional
    public void deleteComment(Long id) throws CustomException {
        Member member;
        Comment comment;
        Long memberIdByJwt = jwtService.getUserIdx();
        member = commentRepository.findMemberbyId(memberIdByJwt);
        comment = commentRepository.findCommentById(id);
        if (memberIdByJwt != comment.getMember().getId()) {
            throw new CustomException(COMMENT_MODIFY_FAIL);
        }
        try {
            comment.deleteComment();
        } catch (Exception e) {
            throw new CustomException(DATABASE_ERROR);
        }
    }
}
