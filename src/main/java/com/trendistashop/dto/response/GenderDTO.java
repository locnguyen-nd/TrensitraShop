package com.trendistashop.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenderDTO {
    private UUID id ;
    @NotBlank(message = "Name is required")
    private String name;
    private String slug;
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;

}
