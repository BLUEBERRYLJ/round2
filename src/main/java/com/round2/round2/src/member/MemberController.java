package com.round2.round2.src.member;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.config.exception.ErrorCode;
import com.round2.round2.config.exception.ErrorResponse;
import com.round2.round2.src.member.model.LoginRequest;
import com.round2.round2.src.member.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.round2.round2.config.exception.ErrorCode.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
//
    private final MemberService memberService;


//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws CustomException{
////        try {
//            if (loginRequest.getEmail().isEmpty()) {
////                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
//                throw new CustomException(ErrorCode.NO_EMAIL_ERROR);
//            }
//            if (loginRequest.getPwd().isEmpty()) {
////                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//                throw new CustomException(ErrorCode.NO_PWD_ERROR);
//            }
//            ResponseEntity<LoginResponse> loginResponse = memberService.login(loginRequest);
//            return loginResponse;
////        } catch (Exception e) {
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws CustomException {
        if (loginRequest.getEmail().isEmpty()) {
            throw new CustomException(NO_EMAIL_ERROR);
        }
        if (loginRequest.getPwd().isEmpty()) {
            throw new CustomException(NO_PWD_ERROR);
        }
        LoginResponse loginResponse = memberService.login2(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }
}
