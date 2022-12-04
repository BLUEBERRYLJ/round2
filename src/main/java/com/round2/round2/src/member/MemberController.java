package com.round2.round2.src.member;

import com.round2.round2.src.member.model.LoginRequest;
import com.round2.round2.src.member.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
//
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest.getEmail().isEmpty()) {
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            }
            if (loginRequest.getPwd().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<LoginResponse> loginResponse = memberService.login(loginRequest);
            return loginResponse;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
