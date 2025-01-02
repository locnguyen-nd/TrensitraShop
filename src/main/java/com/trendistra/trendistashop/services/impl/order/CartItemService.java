package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.dto.response.CartDTO;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.product.ProductVariant;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.entities.user.CartItem;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        // Check stock availability
        if (productVariant.getStockQuantity() < cartDTO.getQuantity()) {
            throw new OrderCreationException("Insufficient stock for product variant: " + productVariant.getId());
        }
        CartItem newItem = CartItem.builder()
                .cart(useCart)
                .cartProduct(product)
                .productVariantId(cartDTO.getVariantDTO().getId())
                .cartItemQuantity(cartDTO.getQuantity())
                .build();
        return newItem;
    }

}
