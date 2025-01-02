package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.entities.user.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    public static CartResponseDTO fromEntity(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setCartTotal(cart.getCartTotal());
        dto.setItems(cart.getCartItems().stream()
                .map(CartItemDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }
}
