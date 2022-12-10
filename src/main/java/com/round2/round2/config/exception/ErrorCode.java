package com.round2.round2.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

//    INVALID_ACCESS_TOKEN(401, "1000", "잘못된 토큰입니다."), //3.3

    NO_EMAIL_ERROR(400, "2001", "이메일을 입력해주세요"),
    NO_PWD_ERROR(400, "2002", "비밀번호를 입력해주세요"),
    USER_NOT_FOUND(401, "2003", "유저를 찾지 못했습니다"),

    EMPTY_BEST_POSTS(400, "3000", "인기 게시물이 없어요."), //3.1 인기게시물
    NO_TITLE_ERROR(400, "3001", "게시물 제목을 입력해주세요."),
    NO_CONTENT_ERROR(400, "3002", "게시물 본문을 입력해주세요."),
    INVALID_POST_CATEGORY(400, "3003", "유효하지 않은 카테고리 입니다."), //3.3 게시물 작성
    EMPTY_POST_LIST(406, "3004", "게시물이 없어요."), ///3.2 게시물리스트
    POST_NOT_EXIST(400, "3005", "게시물을 찾지 못했습니다."),
    DELETED_POST(406, "3006", "삭제된 게시물입니다."),


    INTERNAL_SERVER_ERROR(500, "500", "서버 에러"),
    DATABASE_ERROR (500, "5001", "데이터베이스 에러");

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