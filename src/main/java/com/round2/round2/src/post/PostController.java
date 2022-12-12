package com.round2.round2.src.post;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.post.model.*;
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

import java.util.List;
import java.util.stream.Collectors;

import static com.round2.round2.config.exception.ErrorCode.*;

@Tag(name = "post", description = "게시물 관련 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content (schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403", description = "토큰을 입력해주세요 (Bearer 제외).", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "500", description = "서버 에러.", content = @Content(schema = @Schema(hidden = true))),
})
public class PostController {

    private final PostService postService;

    /**
     * 3.1 베스트 게시물 API
     */
    @Operation(summary = "3.1 베스트 게시물 API", description = "3.1 베스트 게시물 API")
    @ApiResponses ({
            @ApiResponse(responseCode = "400", description = "code:3000 | 인기 게시물이 없어요.", content = @Content (schema = @Schema(hidden = true)))
    })
    @GetMapping("/best")
    public ResponseEntity<List<HomeBestPostResponse>> getBestPosts() {

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        List<Post> posts = postService.findBestPost();
        List<HomeBestPostResponse> result = posts.stream()
                .map(m -> new HomeBestPostResponse(m))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, resHeaders, HttpStatus.OK);
    }


    /**
     * 3.2 게시판 리스트 API
     */
    @GetMapping
    @Operation(summary = "3.2 게시물 리스트 API", description = "3.2 게시물 리스트 API")
    @ApiResponses ({
            @ApiResponse(responseCode = "406", description = "code:3004 | 게시물이 없어요.", content = @Content (schema = @Schema(hidden = true)))
    })
    public ResponseEntity<List<PostListResponse>> getPosts (@RequestParam int category) {

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        List<Post> Posts = postService.findPostList(category);
        List<PostListResponse> result = Posts.stream()
                .map(p -> new PostListResponse(p))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, resHeaders, HttpStatus.OK);
    }


    /**
     * 3.3 게시물 작성 API
     */
    @PostMapping
    @Operation(summary = "3.3 게시물 작성 API", description = "3.3 게시물 작성 API")
    @ApiResponses ({
            @ApiResponse(responseCode = "400", description = "code:3001 | 게시물 제목을 입력해주세요.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:3002 | 게시물 본문을 입력해주세요.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:3003 | 유효하지 않은 카테고리 입니다.", content = @Content (schema = @Schema(hidden = true)))
    })
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        if (request.getTitle().isEmpty()) { // 제목 비어있을때
            throw new CustomException(NO_TITLE_ERROR);
        }
        if (request.getContent().isEmpty()) { // 본문 비어있을때
            throw new CustomException(NO_CONTENT_ERROR);
        }
        CreatePostResponse postResponse = postService.createPost(request);
        return new ResponseEntity<>(postResponse, resHeaders, HttpStatus.CREATED);
    }


    /**
     * 3.4 게시물 상세 API
     */
    @GetMapping("/{postId}")
    @Operation(summary = "3.4 게시물 상세 API", description = "3.4 게시물 상세 API")
    @ApiResponses ({
            @ApiResponse(responseCode = "406", description = "code:3005 | 게시물을 찾지 못했습니다.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 에러", content = @Content(schema = @Schema(hidden = true))),
    })
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        Post post = postService.getPost(postId);
        PostResponse postResponse = postService.getPostResponse(post);
        return new ResponseEntity<>(postResponse, resHeaders, HttpStatus.OK);
    }


    /**
     * 3.5 게시물 상세 댓글 API
     */
    @ApiResponses ({
            @ApiResponse(responseCode = "406", description = "code:3005 | 게시물을 찾지 못했습니다.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "406", description = "code:3006 | 삭제된 게시물입니다.", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 에러", content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> PostDetailComment(@PathVariable Long postId) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        List<CommentResponse> commentResponseList = postService.getCommentList(postId);
        return new ResponseEntity<>(commentResponseList, resHeaders, HttpStatus.OK);
    }
    
    /**
     * 3.5 게시물 삭제 API
     */
    @PatchMapping("status/{id}")
    public ResponseEntity<String> deletePost (@PathVariable Long id) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        postService.deletePost(id);
        return new ResponseEntity<>("게시물 삭제에 성공하였습니다.", resHeaders, HttpStatus.OK);
    }


    
    /**
     * Exception Handler
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, resHeaders, HttpStatus.resolve(errorCode.getStatus()));
    }


}

