package com.ddalkkak.splitting.board.exception;

import com.ddalkkak.splitting.common.exception.ErrorCode;
import com.ddalkkak.splitting.common.exception.GlobalException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

public class BoardException extends GlobalException {
    public BoardException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }


    public static class BoardNotFoundException extends BoardException {
        public BoardNotFoundException(final BoardErrorCode errorCode, final Long category) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), category));
        }
    }
}
