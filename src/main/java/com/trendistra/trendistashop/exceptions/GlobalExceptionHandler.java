package com.trendistra.trendistashop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** The @ControllerAdvice annotation defines a global exception handler for all controllers.
* This allows for centralized exception handling across the application, where all controllers
 can share a common approach to handling errors.
* With this approach, we can return consistent error responses and handle specific exceptions in one place. **/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundEx.class)
    //Ex Not Found Resource
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundEx(ResourceNotFoundEx resourceEx) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.NOT_FOUND.value());
        errorDetails.put("error", "Not Found");
        errorDetails.put("message", resourceEx.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidParameterException.class)
    //Ex Not Found Invalid Parameter
    public  ResponseEntity<Map<String, Object>> handingInvalidParam(InvalidParameterException invalidEx){
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Invalid Parameter");
        errorDetails.put("message", invalidEx.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidRangeException.class)
    //Ex Not Found Invalid Range
    public ResponseEntity<Map<String, Object>> handleInvalidRangeEx(InvalidRangeException invalidEx) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Invalid Parameter");
        errorDetails.put("message", invalidEx.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataAccessException.class)
    public  ResponseEntity<Map<String, Object>> handingDataAccess(DataAccessException dataAccessException) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Data Access Exception");
        errorDetails.put("message", dataAccessException.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public  ResponseEntity<Map<String, Object>> handingUnauthorized(UnauthorizedException unauthorizedException) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", "Data Access Exception");
        errorDetails.put("message", unauthorizedException.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthenticationFailedException.class)
    public  ResponseEntity<Map<String, Object>> handingUnauthorized(AuthenticationFailedException authenticationFailedException) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", "Data Access Exception");
        errorDetails.put("message", authenticationFailedException .getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}

