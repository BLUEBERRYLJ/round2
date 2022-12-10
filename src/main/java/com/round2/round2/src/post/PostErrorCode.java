package com.round2.round2.src.post;

public enum PostErrorCode {
    OperationNotAuthorized(3000,"Server error"),
    DuplicateIdFound(3001,"게시물 제목/본문을 입력해주세요.");
//    UnrecognizedRole(6010,"Unrecognized Role");
    private int code;
    private String description;
    private PostErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}
