package com.trendistashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO<T> {
    private int page;
    private int size;
    private long total;
    private int totalPage;
    private List<T> content;

    public static <T> PageDTO<T> fromPage(Page<T> page) {
        return PageDTO.<T>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .total(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .content(page.getContent())
                .build();
    }

    // Constructor map sang DTO
    public static <T, R> PageDTO<R> fromPage(Page<T> page, List<R> mappedContent) {
        return PageDTO.<R>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .total(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .content(mappedContent)
                .build();
    }
}
