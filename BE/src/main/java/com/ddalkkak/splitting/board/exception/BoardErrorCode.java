package com.ddalkkak.splitting.board.exception;

import org.springframework.http.HttpStatus;

public enum BoardErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 글 정보 입니다.", "2000");

    private final HttpStatus status;
    private final String message;
    private final String code;

    BoardErrorCode(final HttpStatus status, final String message, final String code) {
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
