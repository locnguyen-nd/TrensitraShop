package com.trendistra.trendistashop.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record RoleDTO(
        UUID id,
        @NotBlank(message = "Role name is required")
        String name,
        String description,
        Set<UUID> permissionIds
) {}


