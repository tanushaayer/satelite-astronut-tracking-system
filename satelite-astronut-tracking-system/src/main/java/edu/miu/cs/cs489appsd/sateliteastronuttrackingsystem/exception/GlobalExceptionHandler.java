package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception.astronaut.AstronautNotFoundException;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception.satellite.DecommissionedSatelliteUpdateException;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception.satellite.SatelliteNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AstronautNotFoundException.class)
    public ResponseEntity<ApiError> handleAstronautNotFoundException(AstronautNotFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SatelliteNotFoundException.class)
    public ResponseEntity<ApiError> handleSatelliteNotFoundException(SatelliteNotFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiError> handleInvalidFormat(InvalidFormatException e, HttpServletRequest request) {
        String message = "Invalid value provided";

        // Detect enum failure
        if (e.getTargetType().isEnum()) {
            Object invalidValue = e.getValue();
            Object[] acceptedValues = e.getTargetType().getEnumConstants();
            message = "Invalid orbit type: " + invalidValue + ". Allowed values are: " + Arrays.toString(acceptedValues);
        }

        ApiError apiError = new ApiError(
                message,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DecommissionedSatelliteUpdateException.class)
    public ResponseEntity<ApiError> handleSatelliteDecommisionedException(DecommissionedSatelliteUpdateException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        Throwable cause = e.getCause();

        String message = "Malformed JSON request";

        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            Object invalidValue = ife.getValue();
            Object[] allowedValues = ife.getTargetType().getEnumConstants();
            message = "Invalid value for enum '" + ife.getTargetType().getSimpleName()
                    + "': " + invalidValue + ". Allowed values: " + Arrays.toString(allowedValues);
        }

        ApiError apiError = new ApiError(
                message,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        String combinedMessage = "Validation failed: " + String.join("; ", messages);

        ApiError apiError = new ApiError(
                combinedMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> genericException(Exception e,  HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
