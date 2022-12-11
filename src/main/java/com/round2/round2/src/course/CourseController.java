package com.round2.round2.src.course;

import com.round2.round2.src.course.model.CourseListResponse;
import com.round2.round2.src.domain.Course;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.post.model.PostListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "course", description = "강의 관련 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403", description = "토큰을 입력해주세요 (Bearer 제외).", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "500", description = "서버 에러.", content = @Content(schema = @Schema(hidden = true))),
})
public class CourseController {

//    private final CourseService courseService;


//
//    /**
//     * 2.1 카테고리별 강의 리스트 API
//     */
//    @GetMapping
//    @Operation(summary = " 2.1 카테고리별 강의 리스트 API (강의 페이지)", description = " 2.1 카테고리별 강의 리스트 (강의 페이지) API")
//    @ApiResponses ({
//            @ApiResponse(responseCode = "400", description = "code:", content = @Content (schema = @Schema(hidden = true)))
//    })
//    public ResponseEntity<List<PostListResponse>> getCourses (@RequestParam int category) {
//        List<Course> courseList = courseService.findPostList(category);
//        List<PostListResponse> result = courseList.stream()
//                .map(p -> new CourseListResponse(p))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


}
