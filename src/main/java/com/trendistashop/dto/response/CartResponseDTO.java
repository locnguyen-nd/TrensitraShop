package com.trendistashop.dto.response;

import com.trendistashop.entities.user.Cart;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.services.impl.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<CartItemDTO> items;

    public static CartResponseDTO fromEntity(Cart cart, ProductService productService) {
        CartResponseDTO dto = new CartResponseDTO();
        List<CartItem> sortedItems = cart.getCartItems().stream()
                .sorted(Comparator.comparing(CartItem::getCreatedAt).reversed())
                .collect(Collectors.toList());
        dto.setId(cart.getId());
        dto.setCartTotal(cart.getCartTotal());
        dto.setItems(sortedItems.stream()
                .map(item -> CartItemDTO.fromEntity(item, productService))
                .collect(Collectors.toList()));
        return dto;
    }
}
