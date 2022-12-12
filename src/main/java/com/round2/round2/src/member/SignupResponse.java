package com.round2.round2.src.member;

import lombok.Getter;

@Getter
public class SignupResponse {
    private Long memberId;
    private String message;

    public SignupResponse(Long memberId) {
        this.memberId = memberId;
        this.message = "회원가입에 성공하였습니다.";
    }
}
