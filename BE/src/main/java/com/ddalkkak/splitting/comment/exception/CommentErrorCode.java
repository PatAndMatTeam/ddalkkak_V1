package com.ddalkkak.splitting.comment.exception;

import org.springframework.http.HttpStatus;

public enum CommentErrorCode {
    NOT_MATCH_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 틀립니다..", "2000");

    private final HttpStatus status;
    private final String message;
    private final String code;

    CommentErrorCode(final HttpStatus status, final String message, final String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
