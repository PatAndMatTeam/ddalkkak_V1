package com.ddalkkak.splitting.user.exception;

import org.springframework.http.HttpStatus;

public enum UserErrorCode {
    INVALID_PASSWORD(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "비밀번호가 틀립니다.", "2000"),
    NOTFOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "REFRESH 토큰이 존재하지 않습니다..", "2000"),
    NOTFOUND_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "ACCESS 토큰이 존재하지 않습니다..", "2000");

    private final HttpStatus status;
    private final String message;
    private final String code;

    UserErrorCode(final HttpStatus status, final String message, final String code) {
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
