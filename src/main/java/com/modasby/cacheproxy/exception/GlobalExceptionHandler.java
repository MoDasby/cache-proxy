package com.modasby.cacheproxy.exception;

import com.modasby.cacheproxy.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ExceptionDTO> handleBadGateway(BadGatewayException e) {
        ExceptionDTO body = new ExceptionDTO(e.getMessage());

        return new ResponseEntity<ExceptionDTO>(body, HttpStatus.BAD_GATEWAY);
    }
}
