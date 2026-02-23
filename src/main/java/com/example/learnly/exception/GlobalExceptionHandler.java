package com.example.learnly.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CriticalSystemException.class)
    public ResponseEntity<ProblemDetail> handleCriticalSystemException(CriticalSystemException e) {
        log.error("500  A critical error occurred that should not have occurred: {}", e.getMessage(), e);

        String message = """
                Occurred an unexpected error on the server side. We are already working on it. Please, try again later.
                
                error occurred:
                %s
                """.formatted(e.getMessage());
        var problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        problem.setTitle("Unexpected Internal Server Error");
        problem.setDetail(message);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ProblemDetail> handleInvalidTokenException(InvalidTokenException e) {
        log.warn("401  Invalid jwt: {}", e.getMessage());

        var problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED.value());
        problem.setTitle("Invalid Token");
        problem.setDetail(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("404  {}", e.getMessage());

        var problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problem.setTitle("Not Found Resource");
        problem.setDetail(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidation(
            MethodArgumentNotValidException e) {
        log.warn("400  {}", e.getMessage());

        Map<String, List<String>> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> errors
                .computeIfAbsent(error.getField(), k -> new ArrayList<>())
                .add(error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

}
