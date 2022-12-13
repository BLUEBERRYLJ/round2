package com.round2.round2.src.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class LoginResponse {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
    private String isSubscribed;
}
