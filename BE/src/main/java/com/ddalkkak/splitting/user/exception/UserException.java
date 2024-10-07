package com.ddalkkak.splitting.user.exception;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UserException extends GlobalException {
    public UserException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }


    public static class InvalidPasswordException extends UserException {
        public InvalidPasswordException(final UserErrorCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }
}
