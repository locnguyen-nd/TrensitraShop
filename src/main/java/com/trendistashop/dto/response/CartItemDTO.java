package com.trendistashop.dto.response;

import com.trendistashop.entities.user.CartItem;
import com.trendistashop.services.impl.product.ProductService;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CartItemDTO {

    private UUID id;
    private UUID productId;
    private UUID variantId;
    private UUID imageId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private ProductDTO product;

    public static CartItemDTO fromEntity(CartItem cartItem, ProductService productService) {
        CartItemDTO dto = CartItemDTO.builder()
            .id(cartItem.getId())
            .productId(cartItem.getCartProduct().getId())
            .variantId(cartItem.getProductVariantId())
            .imageId(cartItem.getProductImageId())
            .productName(cartItem.getCartProduct().getName())
            .quantity(cartItem.getCartItemQuantity())
            .price(cartItem.getCartProduct().getPrice())
            .product(productService.mapToProductDto(cartItem.getCartProduct()))
            .build();
        return dto;
    }
}
