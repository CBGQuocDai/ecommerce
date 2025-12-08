package com.backend.exceptions;

import com.backend.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(Exception e){
        Error error = Error.BAD_REQUEST;
        return ResponseEntity.status(error.getStatus())
                .body(new Response<>(error.getCode(),e.getMessage(),null));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
        return ResponseEntity.status(400)
                .body(new Response<>(1002, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),null));
    }
}
