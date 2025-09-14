package com.trendistashop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreUpDTO {
    @NotNull(message = "Gender ID is required")
    private UUID gender;
    private UUID parent;
    @NotBlank(message = "Name is required")
    private String name;
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;
    private String description;
    private Integer index;
}

