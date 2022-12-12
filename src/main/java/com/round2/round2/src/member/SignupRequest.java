package com.round2.round2.src.member;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class SignupRequest {
    private String email;
    private String pwd;
    private String name;
}
