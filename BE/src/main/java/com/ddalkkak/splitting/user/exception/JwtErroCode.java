package com.ddalkkak.splitting.user.exception;

import org.springframework.http.HttpStatus;

public enum JwtErroCode {
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 만료됐습니다.", "2000"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access 토큰이 만료됐습니다.", "2000");

    private final HttpStatus status;
    private final String message;
    private final String code;

    JwtErroCode(final HttpStatus status, final String message, final String code) {
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
