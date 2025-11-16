package com.trendistashop.dto.response;

import com.trendistashop.entities.user.Cart;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.services.impl.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private UUID id;
    private BigDecimal cartTotal;
    // Có thể là List<CartItemDTO> hoặc PageDTO<CartItemDTO>
    private Object items;
    // === OPTION 1: Không phân trang ===
    public static CartResponseDTO fromEntity(Cart cart, ProductService productService) {
        List<CartItemDTO> itemDTOs = cart.getCartItems().stream()
                .map(item -> CartItemDTO.fromEntity(item, productService))
                .collect(Collectors.toList());

        return new CartResponseDTO(cart.getId(), cart.getCartTotal(), itemDTOs);
    }

    // === OPTION 2: Có phân trang ===
    public static CartResponseDTO fromEntity(Cart cart, ProductService productService, Pageable pageable) {
        List<CartItem> items = cart.getCartItems();
        int total = items.size();
        int pageSize = Math.max(1, pageable.getPageSize());
        int start = Math.min((int) pageable.getOffset(), total);
        int end = Math.min(start + pageSize, total);

        List<CartItemDTO> pagedItems = items.subList(start, end).stream()
                .map(item -> CartItemDTO.fromEntity(item, productService))
                .collect(Collectors.toList());

        PageDTO<CartItemDTO> pageDTO = new PageDTO<>();
        pageDTO.setPage(pageable.getPageNumber());
        pageDTO.setSize(pageSize);
        pageDTO.setTotal(total);
        pageDTO.setTotalPage((int) Math.ceil((double) total / pageSize));
        pageDTO.setContent(pagedItems);
        return new CartResponseDTO(cart.getId(), cart.getCartTotal(), pageDTO);
    }
}
