package com.nwic.gt.decoder.file.tracking.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while reading the file: " + ex.getMessage());
    }

//    @ExceptionHandler(InvalidSensorHubCodeFoundException.class)
//    public ResponseEntity<String> handleInvalidSensorHubCodeFoundException(InvalidSensorHubCodeFoundException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("Invalid Sensor Hub Code: " + ex.getMessage());
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource not found: " + ex.getMessage());
    }
    public ResponseEntity<String> handleDirectoryNotFoundException(DirectoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Directory Not Found: " + ex.getMessage());
    }
}
