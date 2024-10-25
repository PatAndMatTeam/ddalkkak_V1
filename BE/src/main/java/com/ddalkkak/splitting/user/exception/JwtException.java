package com.ddalkkak.splitting.user.exception;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class JwtException extends GlobalException {
    public JwtException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }


    public static class ExpiredRefreshTokenException extends JwtException {
        public ExpiredRefreshTokenException(final JwtErroCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }

    public static class ExpiredActiveTokenException extends JwtException {
        public ExpiredActiveTokenException(final JwtErroCode errorCode, final String token) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), token));
        }
    }
}
