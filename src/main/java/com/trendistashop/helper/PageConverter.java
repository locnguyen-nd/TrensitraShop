package com.trendistashop.helper;

import com.trendistashop.dto.response.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageConverter {
    public <T> PageDTO<T> toPageDTO(Page<T> page) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setPage(page.getNumber());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotal(page.getTotalElements());
        pageDTO.setTotalPage(page.getTotalPages());
        pageDTO.setContent(page.getContent());
        return pageDTO;
    }
}
