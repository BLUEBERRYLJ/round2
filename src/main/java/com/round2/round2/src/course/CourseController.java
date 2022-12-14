package com.round2.round2.src.course;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.course.model.ChapterDetailResponse;
import com.round2.round2.src.course.model.CourseDetailResponse;
import com.round2.round2.src.course.model.CourseListResponse;
import com.round2.round2.src.domain.Course;
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
public class  CourseController {

    private final CourseService courseService;



    /**
     * 2.1 카테고리별 강의 리스트 API
     */
    @GetMapping
    @Operation(summary = " 2.1 카테고리별 강의 리스트 API (강의 페이지)", description = " 2.1 카테고리별 강의 리스트 (강의 페이지) API")
    public ResponseEntity<List<CourseListResponse>> getCourses (@RequestParam int category) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        List<Course> courseList = courseService.findPostList(category);
        List<CourseListResponse> result = courseList.stream()
                .map(p -> new CourseListResponse(p))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, resHeaders, HttpStatus.OK);
    }

    /**
     * 2.2 강의 상세 및 강의 챕터 리스트 리턴 api
     */
    @Operation(summary = "2.2 강의 상세 및 강의 챕터 리스트 리턴 api", description = "2.2 강의 상세 및 강의 챕터 리스트 리턴 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "406", description = "code:2001 | 강의/챕터를 찾을 수 없습니다.", content = @Content (schema = @Schema(hidden = true))),
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailResponse> getCourseDetail(@PathVariable Long courseId) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        CourseDetailResponse courseDetailResponse = courseService.getCourseDetail(courseId);
        return new ResponseEntity<>(courseDetailResponse, resHeaders, HttpStatus.OK);
    }

    /**
     * 2.3 강의 챕터 상세 api
     */
    @Operation(summary = "2.3 강의 챕터 상세 api", description = "2.3 강의 챕터 상세 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "406", description = "code:2001 | 강의/챕터를 찾을 수 없습니다.", content = @Content (schema = @Schema(hidden = true))),
    })
    @GetMapping("/chapter")
    public ResponseEntity<ChapterDetailResponse> getChapterDetail(@RequestParam int chapterId) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        ChapterDetailResponse chapterDetailResponse = courseService.getChapterDetail(chapterId);
        return new ResponseEntity<>(chapterDetailResponse, resHeaders, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        
        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, resHeaders, HttpStatus.resolve(errorCode.getStatus())); //resolve: convert code in errorCode to http status code
    }


}
