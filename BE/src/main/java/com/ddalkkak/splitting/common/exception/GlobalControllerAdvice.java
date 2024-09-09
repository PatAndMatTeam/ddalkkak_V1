package com.ddalkkak.splitting.common.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalControllerAdvice {
    private final ObjectMapper objectMapper;

    private static final String ERROR_MESSAGE_DELIMITER = ", ";
    private static final String RESPONSE_DELIMITER = ". ";

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorCode> handleGlobalException(final GlobalException e, final HttpServletRequest request)
            throws JsonProcessingException {
        final String exceptionSource = getExceptionSource(e);
        log.warn("source = {} , {} = {} code = {} message = {} info = {}", exceptionSource, request.getMethod(),
                request.getRequestURI(), e.getErrorCode().getCode(), e.getErrorCode().getMessage(),
                objectMapper.writeValueAsString(e.getErrorCode().getInfo()));

        final ErrorCode<?> errorCode = new ErrorCode<>(e.getErrorCode().getCode(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(errorCode);
    }

    private String getExceptionSource(final Exception e) {
        final StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            return stackTrace[0].toString();
        }
        return "Unknown location";
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorCode> handleValidationExceptions(MethodArgumentNotValidException e) {
//        String errorMessage = e.getBindingResult().getAllErrors().stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//        final ErrorCode<?> errorCode = new ErrorCode<>(CommonErrorCode.REQUEST_VALID_ERROR_CODE.getCode(), errorMessage);
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(errorCode);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorCode> handleValidationExceptionsV1(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        final ErrorCode<?> errorCode = new ErrorCode<>(CommonErrorCode.REQUEST_VALID_ERROR_CODE.getCode(), errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorCode);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorCode> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getMessage();

        final ErrorCode<?> errorCode = new ErrorCode<>(CommonErrorCode.REQUEST_VALID_ERROR_CODE.getCode(), errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorCode);
    }
}
