package com.modasby.cacheproxy.exception;

import com.modasby.cacheproxy.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private void logError(Exception e) {
        log.error("Error: {}", e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> handleRuntimeException(RuntimeException e) {
        ExceptionDTO body = new ExceptionDTO("Internal server error");

        logError(e);

        return ResponseEntity.internalServerError().body(body);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ExceptionDTO> handleBadGateway(BadGatewayException e) {
        ExceptionDTO body = new ExceptionDTO(e.getMessage());

        logError(e);

        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }
}
