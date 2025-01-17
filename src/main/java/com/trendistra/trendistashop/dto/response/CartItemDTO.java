package com.trendistra.trendistashop.dto.response;

import com.trendistra.trendistashop.entities.user.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemDTO {
    private UUID id;
    private UUID productId;
    private UUID variantId;
    private UUID imageId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    public static CartItemDTO fromEntity(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getCartProduct().getId());
        dto.setVariantId(cartItem.getProductVariantId());
        dto.setImageId(cartItem.getProductImageId());
        dto.setProductName(cartItem.getCartProduct().getName());
        dto.setQuantity(cartItem.getCartItemQuantity());
        dto.setPrice(cartItem.getCartProduct().getPrice());
        return dto;
    }
}
