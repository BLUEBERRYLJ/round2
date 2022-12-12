package com.round2.round2.src.member;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.member.model.HomeResponse;
import com.round2.round2.src.member.model.LoginRequest;
import com.round2.round2.src.member.model.LoginResponse;
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
import static com.round2.round2.config.exception.ErrorCode.*;
import static com.round2.round2.utils.ValidationRegex.isRegexEmail;
import static com.round2.round2.utils.ValidationRegex.isRegexPassword;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "member", description = "로그인/인증 API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(schema = @Schema(hidden = true)))
})
public class MemberController {
//
    private final MemberService memberService;


    @Operation(summary = "1.1 회원가입 api", description = "1.1 회원가입 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "code:1002 | 이메일을 입력해주세요", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:1003 | 비밀번호를 입력해주세요", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:1004 | 이름을 입력해주세요", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:1005 | 이메일 형식을 확인해주세요", content = @Content (schema = @Schema(hidden = true)))
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> login(@RequestBody SignupRequest signupRequest) throws CustomException {
        if (signupRequest.getEmail().isEmpty()) {
        throw new CustomException(NO_EMAIL_ERROR);
        }
        //이메일 형식(정규식) 검증
        if (!isRegexEmail(signupRequest.getEmail())) {
            throw new CustomException(EMAIL_FORMAT_ERROR);
        } //이름 빔 
        if (signupRequest.getName().isEmpty()) {
            throw new CustomException(NAME_EMPTY_ERROR);
        }
        // 비밀번호 빔
        if (signupRequest.getPwd().isEmpty()) {
            throw new CustomException(NO_PWD_ERROR);
        }
        SignupResponse signupResponse = memberService.createMember(signupRequest);
        return new ResponseEntity<>(signupResponse, HttpStatus.OK);
    }




    @Operation(summary = "1.2 로그인 api", description = "1.2 로그인 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "code:1001 | 유저를 찾지 못했습니다", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:1002 | 이메일을 입력해주세요", content = @Content (schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "code:1003 | 비밀번호를 입력해주세요", content = @Content (schema = @Schema(hidden = true)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws CustomException {
        if (loginRequest.getEmail().isEmpty()) {
            throw new CustomException(NO_EMAIL_ERROR);
        }
        if (loginRequest.getPwd().isEmpty()) {
            throw new CustomException(NO_PWD_ERROR);
        }
        LoginResponse loginResponse = memberService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Operation(summary = "1.3 홈화면 api", description = "1.3 홈화면 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "code:1003 | 비밀번호를 입력해주세요", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<HomeResponse> getHome() throws CustomException {
        HomeResponse home = memberService.getHome();

//        HomeResponse homeResponse = new HomeResponse()


    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus())); //resolve: convert code in errorCode to http status code
    }
}
