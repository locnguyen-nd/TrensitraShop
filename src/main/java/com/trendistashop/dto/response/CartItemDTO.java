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
        if (product == null) {
            throw new IllegalArgumentException("CartItem must have a product");
        }

        ProductVariant variant = product.getProductVariants().stream()
                .filter(v -> Objects.equals(v.getId(), cartItem.getProductVariantId()))
                .findFirst()
                .orElse(null);

        ProductImage productImage = null;
        if (variant != null && variant.getColor() != null && variant.getSize() != null) {
            productImage = product.getImages().stream()
                    .filter(img -> Boolean.TRUE.equals(img.getIsThumbnail())
                            && img.getColor() != null
                            && img.getSize() != null
                            && Objects.equals(img.getColor().getId(), variant.getColor().getId())
                            && Objects.equals(img.getSize().getId(), variant.getSize().getId()))
                    .findFirst()
                    .orElse(null);
        }

        if (productImage == null) {
            productImage = product.getImages().stream()
                    .filter(ProductImage::getIsThumbnail)
                    .findFirst()
                    .orElse(null);
        }

        return CartItemDTO.builder()
                .id(cartItem.getId())
                .productId(product.getId())
                .variantId(cartItem.getProductVariantId())
                .imageId(productImage != null ? productImage.getId() : null)
                .productName(product.getName())
                .quantity(cartItem.getCartItemQuantity())
                .price(cartItem.getUnitPrice()) // Giá đã lưu tại thời điểm thêm
                .product(productService.mapToProductDto(product))
                .build();
    }
}
