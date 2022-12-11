package com.round2.round2.config;

import lombok.Getter;

/**
<<<<<<< HEAD
 * 에러 코드 관리
 * 당장 usage 가 없는건 주석처리 했습니다.
 * 필요한것들 주석 해제하고 쓰기
 */


@Getter
public enum BaseResponseStatus {

    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
