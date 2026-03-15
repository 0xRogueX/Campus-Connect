package com.campusconnect.materialsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Catches any IOException thrown in any controller
     * (e.g. file-not-found in downloadMaterial) and maps it to 404.
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String,String>> handleIoException(IOException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
