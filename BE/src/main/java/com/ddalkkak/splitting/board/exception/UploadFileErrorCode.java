package com.ddalkkak.splitting.board.exception;

import org.springframework.http.HttpStatus;

public enum UploadFileErrorCode {
    CANNOT_BE_UPLOADED(HttpStatus.FORBIDDEN, "업로드 할 수 없는 파일입니다.", "2000"),
    IS_NOT_IMAGE(HttpStatus.FORBIDDEN, "이미지 파일이 아닙니다.", "2001");

    private final HttpStatus status;
    private final String message;
    private final String code;

    UploadFileErrorCode(final HttpStatus status, final String message, final String code) {
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
