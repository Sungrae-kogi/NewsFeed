package com.sparta.newsfeed.common.exception;

public enum ErrorCode {

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다.");

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
