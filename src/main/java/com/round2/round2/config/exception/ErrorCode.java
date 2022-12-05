package com.round2.round2.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    NO_EMAIL_ERROR(400, "2001", "이메일을 입력해주세요"),
    NO_PWD_ERROR(400, "2002", "비밀번호를 입력해주세요"),
    USER_NOT_FOUND(401, "2003", "유저를 찾지 못했습니다"),
    INTERNAL_SERVER_ERROR(500, "500", "서버 에러");

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}