package com.round2.round2.src.comment;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.comment.model.PostCommentRequest;
import com.round2.round2.src.comment.model.PostCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    /**
     * 4.1 댓글 작성
     */
    @PostMapping
    public ResponseEntity<PostCommentResponse> createComment(@RequestBody PostCommentRequest postCommentRequest) {
        PostCommentResponse postCommentResponse = commentService.createComment(postCommentRequest);
        return new ResponseEntity<>(postCommentResponse, HttpStatus.OK);
    }

    /**
     * 4.2 댓글 삭제
     * @param id
     * @return
     */
    @PatchMapping("/status")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>("댓글 삭제에 성공하였습니다.", HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }

}
