package com.round2.round2.src.comment;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.comment.model.PostCommentRequest;
import com.round2.round2.src.comment.model.PostCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "comment", description = "댓글 관련 API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content (schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403", description = "토큰을 입력해주세요 (Bearer 제외).", content = @Content(schema = @Schema(hidden = true))),
})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;



    /**
     * 4.1 댓글 작성
     */
    @Operation(summary = "4.1 댓글 작성 api", description = "4.1 댓글 작성 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "code:1001 | 유저를 찾지 못했습니다", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "406", description = "code:3005 | 게시물을 찾지 못했습니다", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "406", description = "code:4001 | 댓글 생성에 실패했습니다", content = @Content (schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<PostCommentResponse> createComment(@RequestBody PostCommentRequest postCommentRequest) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        PostCommentResponse postCommentResponse = commentService.createComment(postCommentRequest);
        return new ResponseEntity<>(postCommentResponse, resHeaders, HttpStatus.CREATED);
    }

    /**
     * 4.2 댓글 삭제
     * @param id
     * @return
     */
    @Operation(summary = "4.2 댓글 삭제 api", description = "4.1 댓글 삭제 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "406", description = "code:4002 | 댓글을 찾지 못했습니다.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "code:4003 | 댓글 수정/삭제에 실패했습니다.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "code:5001 | 데이터베이스 에러", content = @Content (schema = @Schema(hidden = true)))

    })
    @PatchMapping("/status/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        commentService.deleteComment(id);
        return new ResponseEntity<>("댓글 삭제에 성공하였습니다.", resHeaders, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, resHeaders, HttpStatus.resolve(errorCode.getStatus()));
    }

}
