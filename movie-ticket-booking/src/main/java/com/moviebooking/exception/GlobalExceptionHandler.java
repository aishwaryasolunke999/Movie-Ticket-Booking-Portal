package com.moviebooking.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   
    private Map<String, Object> buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status",    status.value());
        errorResponse.put("error",     error);
        errorResponse.put("message",   message);
        errorResponse.put("path",      path);
        return errorResponse;
    }

   
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>>
            handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>>
            handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

  
    @ExceptionHandler(SeatNotAvailableException.class)
    public ResponseEntity<Map<String, Object>>
            handleSeatNotAvailableException(
            SeatNotAvailableException ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.CONFLICT,
                "Seat Not Available",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>
            handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult()
          .getAllErrors()
          .forEach(err -> {
              String fieldName = ((FieldError) err)
                      .getField();
              String errorMsg  = err.getDefaultMessage();
              fieldErrors.put(fieldName, errorMsg);
          });

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "Input validation failed",
                request.getRequestURI()
        );
        error.put("fieldErrors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>>
            handleBadCredentialsException(
            BadCredentialsException ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                "Invalid email or password",
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

   
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>>
            handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.FORBIDDEN,
                "Forbidden",
                "You don't have permission to access this resource",
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

   
    @ExceptionHandler(jakarta.persistence.OptimisticLockException.class)
    public ResponseEntity<Map<String, Object>>
            handleOptimisticLockException(
            jakarta.persistence.OptimisticLockException ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                "Seats already booked by another user. " +
                "Please select different seats.",
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

   
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
            handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        Map<String, Object> error = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}