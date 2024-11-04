package com.ddalkkak.splitting.user.exception;

import org.springframework.http.HttpStatus;

public enum JwtErrorCode {
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 만료됐습니다.", "2000"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access 토큰이 만료됐습니다.", "2000"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access이 변조되었습니다.", "2001"),
    ILLEGAL_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access token이 존재하지 않거나 값이 틀립니다.", "2001");

    private final HttpStatus status;
    private final String message;
    private final String code;

    JwtErrorCode(final HttpStatus status, final String message, final String code) {
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
