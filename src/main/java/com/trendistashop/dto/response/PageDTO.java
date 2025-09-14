package com.trendistashop.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private int page;
    private int size;
    private long total;
    private int totalPage;
    private List<T> content;
}
