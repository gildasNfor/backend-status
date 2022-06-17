package com.example.restservice.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.restservice.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class NotFoundHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}