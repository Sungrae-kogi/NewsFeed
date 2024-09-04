package com.sparta.newsfeed.common.exception;

public enum ErrorCode {

    BAD_REQUEST(400, "잘못된 요청입니다."),
    UNSUPPORTED_SORT_CRITERIA(400, "지원하지 않는 정렬 기준입니다."),
    USER_CANNOTLIKE_OWNCOMMENT(404, "유저는 본인의 댓글에 좋아요를 누를 수 없습니다."),
    USER_CANNOTLIKE_OWNPOST(404, "유저는 본인의 게시물에 좋아요를 누를 수 없습니다."),

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다."),
    POST_NOT_FOUND(404, "존재하지 않는 게시물입니다."),
    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    POST_ALREADY_LIKED(404, "이미 좋아요를 누른 게시물입니다."),
    COMMENT_ALREADY_LIKED(404, "이미 좋아요를 누른 댓글입니다.");

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
