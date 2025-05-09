package com.komsos.kosmos.exception.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.komsos.kosmos.exception.error.BadRequestException;
import com.komsos.kosmos.exception.model.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException ex, HttpServletRequest request) {
        
        log.error("ERROR: {} -CALL {}", ex.getMessage(), request.getRequestURI());
        ErrorResponse response = new ErrorResponse();
        response.setPath(request.getRequestURI());
        response.setMensaje(ex.getMessage());
        response.setError(ex.getMessage());
        response.setTimestamp(Instant.now().getEpochSecond());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
