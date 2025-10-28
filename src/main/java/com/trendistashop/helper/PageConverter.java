package com.trendistashop.helper;

import com.trendistashop.dto.response.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PageConverter {
    public <T> PageDTO<T> toPageDTO(Page<T> page) {
        if (page == null) {
            return new PageDTO<T>() {{
                setPage(0);
                setSize(0);
                setTotal(0);
                setTotalPage(0);
                setContent(Collections.emptyList());
            }};
        }
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setPage(page.getNumber());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotal(page.getTotalElements());
        pageDTO.setTotalPage(page.getTotalPages());
        pageDTO.setContent(page.getContent());
        return pageDTO;
    }
}
