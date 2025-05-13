package ba.unsa.etf.AnimalAdoptionPet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    // Handler za validacione greške (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Bad Request");
        errorDetails.put("message", "Validation failed");
        errorDetails.put("validationErrors", validationErrors);
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();

        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", ex.getStatusCode().value());
        errorDetails.put("error", ex.getStatusCode().toString());
        errorDetails.put("message", ex.getReason());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();

        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
