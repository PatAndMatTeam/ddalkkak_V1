package com.ddalkkak.splitting.board.exception;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UploadFileException extends GlobalException {
    public UploadFileException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }


    public static class CannotBeUploadedException extends UploadFileException {
        public CannotBeUploadedException(final UploadFileErrorCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }

    public static class IsNotImageException extends UploadFileException {
        public IsNotImageException(final UploadFileErrorCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }
}