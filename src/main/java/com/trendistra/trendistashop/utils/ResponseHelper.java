package com.trendistra.trendistashop.utils;

import com.trendistra.trendistashop.dto.response.TypeResponse;

import java.util.HashMap;
import java.util.Map;

public class ResponseHelper {
    public static <T> TypeResponse<T> success(T data, String message, int statusCode) {
        TypeResponse<T> response = new TypeResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setStatusCode(statusCode);
        return response;
    }

    public static <T> TypeResponse<T> error(String message, int statusCode) {
        TypeResponse<T> response = new TypeResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setStatusCode(statusCode);
        return response;
    }

    public static <T> TypeResponse<T> error(String message, int statusCode, Map<String, String> errors) {
        TypeResponse<T> response = new TypeResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setStatusCode(statusCode);
        if (errors != null) {
            response.setErrors(errors);
        }
        return response;
    }

    public static <T> TypeResponse<T> ok(T data, String message) {
        return success(data, message, 200);
    }

    public static <T> TypeResponse<T> created(T data, String message) {
        return success(data, message, 201);
    }

    public static <T> TypeResponse<T> badRequest(String message) {
        return error(message, 400);
    }

    public static <T> TypeResponse<T> notFound(String message) {
        return error(message, 404);
    }

    public static <T> TypeResponse<T> unauthorized(String message) {
        return error(message, 401);
    }

    public static <T> TypeResponse<T> forbidden(String message) {
        return error(message, 403);
    }

    public static <T> TypeResponse<T> serverError(String message) {
        return error(message, 500);
    }

    public static <T> TypeResponse<T> validationError(String key, String message) {
        Map<String, String> errors = new HashMap<>();
        errors.put(key,message);
        return error(message, 422, errors);
    }
}