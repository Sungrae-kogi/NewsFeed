package com.sparta.newsfeed.common.exception;

public enum ErrorCode {

    BAD_REQUEST(400, "잘못된 요청입니다."),
    CANNOT_FOLLOW_YOURSELF(400, "본인은 팔로우할수 없습니다"),

    TOKEN_INVALID(401, "토큰이 유효하지 않습니다."),

    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    USER_NOT_FOUND(404, "존재하지 않는 유저입니다."),
    POST_NOT_FOUND(404, "존재하지 않는 게시물입니다."),
    FOLLOW_NOT_FOUND(404, "팔로우 관계가 존재하지 않습니다."),
    PASSWORD_NOT_MATCH(404, "비밀번호가 일치하지 않습니다"),

    ALREADY_USER_EXIST(409, "이미 존재하는 회원입니다");


    private final int statusCodee;
    private final String message;

    ErrorCode(final int statusCodee, final String message) {
        this.statusCodee = statusCodee;
        this.message = message;
    }

    public int statusCode() {
        return statusCodee;
    }

    public String message() {
        return message;
    }
}
