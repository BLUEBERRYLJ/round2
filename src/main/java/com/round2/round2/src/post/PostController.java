package com.round2.round2.src.post;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.post.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.round2.round2.config.exception.ErrorCode.*;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 3.1 베스트 게시물 API
     */
    @GetMapping("/best")
    public ResponseEntity<List<HomeBestPostResponse>> getBestPosts() {
        List<Post> posts = postService.findBestPost();
        List<HomeBestPostResponse> result = posts.stream()
                .map(m -> new HomeBestPostResponse(m))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 3.2 게시판 리스트 API 
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts (@RequestParam int category) {
        List<Post> Posts = postService.findPostList(category);
        List<PostResponse> result = Posts.stream()
                .map(p -> new PostResponse(p))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 3.3 게시물 생성 API
     */
    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
        if (request.getTitle().isEmpty()) { // 제목 비어있을때
            throw new CustomException(NO_TITLE_ERROR);
        }
        if (request.getContent().isEmpty()) { // 본문 비어있을때
            throw new CustomException(NO_CONTENT_ERROR);
        }
        CreatePostResponse postResponse = postService.createPost(request);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }


    /**
     * 3.4 게시물 상세 API
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        postService.updateView(postId);
        PostResponse postResponse = postService.getPostResponse(post);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
//            @ApiResponse(responseCode = "3035", description = "게시물이 존재하지 않습니다.", content = @Content (schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "3036", description = "삭제되거나 신고된 게시물 입니다.", content = @Content (schema = @Schema(hidden = true))),





    /**
     * Exception Handler
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }

}
