package com.round2.round2.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    /**
     * 1. auth
     */
    USER_NOT_FOUND(401, "1001", "유저를 찾지 못했습니다"),
    NO_EMAIL_ERROR(400, "1002", "이메일을 입력해주세요"),
    NO_PWD_ERROR(400, "1003", "비밀번호를 입력해주세요"),

    /**
     * 2. course
     */

    /**
     * 3. post
     */
    POST_NOT_EXIST(406, "3001", "게시물을 찾지 못했습니다"),

    /**
     * 4. comment
     */
    FAILED_TO_CREATECOMMENT(406, "4001", "댓글 생성에 실패했습니다"),
    COMMENT_NOT_EXIST(406, "4002", "댓글을 찾지 못했습니다."),
    COMMENT_MODIFY_FAIL(403, "4003", "댓글 수정/삭제에 실패했습니다."),

    /**
     * 5. server
     */
    DATABASE_ERROR (500, "5001", "데이터베이스 에러"),
    INTERNAL_SERVER_ERROR(500, "5002", "서버 에러");

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