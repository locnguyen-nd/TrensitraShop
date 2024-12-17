package com.trendistra.trendistashop.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDTO{
        private UUID id;
        @NotBlank(message = "Permission name is required")
        private String name;
        @NotBlank(message = "Method is required")
        private String method;
        @NotBlank(message = "End point is required")
        private String endPoint;
}