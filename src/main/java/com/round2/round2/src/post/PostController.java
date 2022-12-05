package com.round2.round2.src.post;

import com.round2.round2.src.post.model.BasicResponse;
import com.round2.round2.src.post.model.CreatePostRequest;
import com.round2.round2.src.post.model.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Basic;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /**
     * 3.3 게시물 생성
     */
    @PostMapping
    public ResponseEntity<BasicResponse> createPost(@RequestBody CreatePostRequest request) {
        if (request.getContent().isEmpty() || request.getTitle().isEmpty()) { // 제목 / 본문 비어있을때
            return new ResponseEntity<>(new BasicResponse("게시물 제목/본문을 입력해주세요."), HttpStatus.BAD_REQUEST);
        }

        PostResponse postResponse = postService.createPost(request);
        if (postResponse == null) { //cannot create - server error
            return new ResponseEntity<>(new BasicResponse("Server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        return ResponseEntity.created().body(postResponse);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
//
//
//     return new ResponseEntity<MoveResponseDto>(moveResponseDto, headers, HttpStatus.valueOf(200));
//
//  return ResponseEntity.ok()
//          .headers(headers)
//        .body(moveResponseDto);

}
