package com.example.java_course.controllers;

import java.util.Date;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.java_course.models.Error;

@RestControllerAdvice
public class HandlerExceptionController {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Error> handleException(Exception ex) {
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setError("Bad Request");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDate(new Date());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
