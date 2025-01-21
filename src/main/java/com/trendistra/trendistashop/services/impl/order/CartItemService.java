package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.dto.response.CartDTO;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductImage;
import com.trendistra.trendistashop.entities.product.ProductVariant;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.entities.user.CartItem;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class CartItemService {
    @Autowired
    private ProductRepository productRepository;
    public CartItem createItemForCart (CartDTO cartDTO, Cart useCart) throws OrderCreationException {
        Product product = productRepository.findById(cartDTO.getProductId()).get();


        // check product variant
        ProductVariant productVariant = product.getProductVariants().stream()
                .filter(variant -> Objects.equals(variant.getId(), cartDTO.getVariantDTO().getId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Invalid product variant"));

        // Check product image
        ProductImage productImage = product.getImages().stream().
                filter(image -> image.getIsThumbnail()&&Objects.equals(image.getColor().getId(), productVariant.getColor().getId()))
                .findFirst()
                .orElseThrow(() -> new OrderCreationException("Invalid product image"));

        // Check stock availability
        if (productVariant.getStockQuantity() < cartDTO.getQuantity()) {
            throw new OrderCreationException("Insufficient stock for product variant: " + productVariant.getId());
        }
        CartItem newItem = CartItem.builder()
                .cart(useCart)
                .cartProduct(product)
                .productVariantId(cartDTO.getVariantDTO().getId())
                .productImageId(productImage.getId())
                .cartItemQuantity(cartDTO.getQuantity())
                .createAt(new Date())
                .build();
        return newItem;
    }

}
