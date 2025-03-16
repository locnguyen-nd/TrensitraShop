package com.trendistra.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeResponse<T> {
    private boolean success;
    private String message;
    private Map<String, String> errors = new HashMap<>();
    private T data;
    private int statusCode;
}