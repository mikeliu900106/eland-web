package com.example.elandweb.config;

import com.example.elandweb.service.impl.TagInfoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
public class MyExceptionHandler {

    private static final Logger logger = Logger.getLogger(TagInfoServiceImpl.class);

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex) {
        logger.error("DataNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        logger.error("IOException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("檔案傳輸異常");
    }

    @ExceptionHandler(value = InterruptedException.class)
    public ResponseEntity<String> handleInterruptedException(InterruptedException ex) {
        logger.error("DataNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = ExecutionException.class)
    public ResponseEntity<String> handleExecutionException(ExecutionException ex) {
        logger.error("DataNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("RuntimeException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

}
