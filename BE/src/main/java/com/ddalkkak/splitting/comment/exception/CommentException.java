package com.ddalkkak.splitting.comment.exception;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class CommentException extends GlobalException {
    public CommentException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }


    public static class NotMatchPasswordException extends CommentException {
        public NotMatchPasswordException(final CommentErrorCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }
}
