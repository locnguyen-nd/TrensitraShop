package com.trendistashop.services.impl.order;

import com.trendistashop.dto.response.CartDTO;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductImage;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.user.Cart;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.exceptions.OrderCreationException;
import com.trendistashop.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class CartItemService {
    @Autowired
    private ProductRepository productRepository;
    public CartItem createItemForCart (CartDTO cartDTO, Cart userCart) throws OrderCreationException {
        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new OrderCreationException("Product not found"));

        // check product variant
        ProductVariant productVariant = product.getProductVariants().stream()
                .filter(v -> Objects.equals(v.getId(), cartDTO.getVariantDTO().getId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Invalid product variant"));

        BigDecimal variantPrice = (productVariant.getPrice() != null && productVariant.getPrice().compareTo(BigDecimal.ZERO) > 0)
                ? productVariant.getPrice()
                : product.getPrice();

        // Check product image
        ProductImage productImage = product.getImages().stream()
                .filter(image -> image.getIsThumbnail()
                        && Objects.equals(image.getColor().getId(), productVariant.getColor().getId())
                        && Objects.equals(image.getSize().getId(), productVariant.getSize().getId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Invalid product image"));

        return CartItem.builder()
                .cart(userCart)
                .cartProduct(product)
                .productVariantId(cartDTO.getVariantDTO().getId())
                .productImageId(productImage.getId())
                .cartItemQuantity(cartDTO.getQuantity())
                .unitPrice(variantPrice)
                .build();
    }

}
