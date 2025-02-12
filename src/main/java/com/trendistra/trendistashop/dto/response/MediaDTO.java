package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaDTO {
    private UUID id;
    private MediaType type;
    private String url;
    private Integer sortOrder;
    private String title;
    private String alt;
}
