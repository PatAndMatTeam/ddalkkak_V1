package com.ddalkkak.splitting.user.exception;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class JwtException extends GlobalException {
    public JwtException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }


    public static class ExpiredRefreshTokenException extends JwtException {
        public ExpiredRefreshTokenException(final JwtErrorCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }

    public static class ExpiredActiveTokenException extends JwtException {
        public ExpiredActiveTokenException(final JwtErrorCode errorCode, final String token) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), token));
        }
    }

    public static class IllegalTokenException extends JwtException {
        public IllegalTokenException(final JwtErrorCode errorCode, final String token) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), token));
        }
    }


}
