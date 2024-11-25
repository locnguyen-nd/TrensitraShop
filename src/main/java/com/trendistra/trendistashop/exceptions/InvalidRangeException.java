package com.trendistra.trendistashop.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRangeException extends RuntimeException{
    public InvalidRangeException(String message) {
        super(message);
    }
}
