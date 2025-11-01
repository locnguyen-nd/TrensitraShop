package com.trendistashop.dto.response;

import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductImage;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.services.impl.product.ProductService;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;
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
        Product product = cartItem.getCartProduct();
        ProductVariant variant = productService.productVariantById(cartItem.getProductVariantId());

        ProductImage productImage = product.getImages().stream()
                .filter(image ->
                        Boolean.TRUE.equals(image.getIsThumbnail())
                                && image.getColor() != null
                                && image.getSize() != null
                                && Objects.equals(image.getColor().getId(), variant.getColor().getId())
                                && Objects.equals(image.getSize().getId(), variant.getSize().getId())
                )
                .findFirst()
                .orElseGet(() ->
                        product.getImages().stream()
                                .filter(ProductImage::getIsThumbnail)
                                .findFirst()
                                .orElse(null)
                );
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .productId(product.getId())
                .variantId(variant.getId())
                .imageId(productImage != null ? productImage.getId() : null)
                .productName(product.getName())
                .quantity(cartItem.getCartItemQuantity())
                .price(variant.getPrice())
                .product(productService.mapToProductDto(product))
                .build();
    }
}
