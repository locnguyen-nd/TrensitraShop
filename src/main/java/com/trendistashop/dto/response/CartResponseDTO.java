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
        List<CartItem> sortedItems = cart.getCartItems().stream()
                .sorted(Comparator.comparing(CartItem::getCreatedAt).reversed())
                .toList();

        List<CartItemDTO> itemDTOs = sortedItems.stream()
                .map(item -> CartItemDTO.fromEntity(item, productService))
                .collect(Collectors.toList());

        return new CartResponseDTO(cart.getId(), cart.getCartTotal(), itemDTOs);
    }

    // === OPTION 2: Có phân trang ===
    public static CartResponseDTO fromEntity(Cart cart, ProductService productService, Pageable pageable) {
        List<CartItem> sortedItems = cart.getCartItems().stream()
                .sorted(Comparator.comparing(CartItem::getCreatedAt).reversed())
                .toList();

        int start = Math.min((int) pageable.getOffset(), sortedItems.size());
        int end = Math.min(start + pageable.getPageSize(), sortedItems.size());

        List<CartItemDTO> pagedItems = sortedItems.subList(start, end).stream()
                .map(item -> CartItemDTO.fromEntity(item, productService))
                .collect(Collectors.toList());

        PageDTO<CartItemDTO> pageDTO = new PageDTO<>();
        pageDTO.setPage(pageable.getPageNumber());
        pageDTO.setSize(pageable.getPageSize());
        pageDTO.setTotal(sortedItems.size());
        pageDTO.setTotalPage((int) Math.ceil((double) sortedItems.size() / pageable.getPageSize()));
        pageDTO.setContent(pagedItems);
        return new CartResponseDTO(cart.getId(), cart.getCartTotal(), pageDTO);
    }
}
