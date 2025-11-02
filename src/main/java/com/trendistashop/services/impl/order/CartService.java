package com.trendistashop.services.impl.order;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.*;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.user.Cart;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.exceptions.OrderCreationException;
import com.trendistashop.repositories.order.CartRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistashop.services.ICartService;
import com.trendistashop.services.impl.product.ProductService;
import com.trendistashop.services.impl.product.VariantService;
import com.trendistashop.utils.ResponseHelper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private VariantService variantService;
    @Autowired
    private ProductRepository productRepository;
    private static final int MAX_CART_ITEMS = 20;

    @Override
    @Transactional
    public TypeResponse<CartResponseDTO> addProductToCart(CartDTO cart, Principal principal) {
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Cart userCart = user.getUserCart();

            UUID variantId = cart.getVariantDTO().getId();
            int addQuantity = cart.getQuantity();
            int currentItemCount = userCart.getCartItems().size();
            Optional<CartItem> existingItem = userCart.getCartItems().stream()
                    .filter(item -> item.getProductVariantId().equals(variantId))
                    .findFirst();
            if (existingItem.isEmpty() && currentItemCount >= MAX_CART_ITEMS) {
                log.warn("Cart item limit exceeded: {} for user: {}", MAX_CART_ITEMS, principal.getName());
                return ResponseHelper.validationError("items", ResponseMessage.MAX_CART);
            }
            Product product = productRepository.findById(cart.getProductId())
                    .orElseThrow(() -> new OrderCreationException("Product not found"));
            ProductVariant variant = product.getProductVariants().stream()
                    .filter(v -> v.getId().equals(variantId))
                    .findFirst()
                    .orElseThrow(() -> new OrderCreationException("Variant not found"));

            BigDecimal variantPrice = (variant.getPrice() != null && variant.getPrice().compareTo(BigDecimal.ZERO) > 0)
                    ? variant.getPrice()
                    : product.getPrice();

            int currentStock = variantService.getStockForVariant(variantId);

            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                int newQuantity = item.getCartItemQuantity() + addQuantity;

                if (currentStock < newQuantity) {
                    log.warn("Not enough stock for variant ID: {}", variantId);
                    return ResponseHelper.badRequest(ResponseMessage.STOCK_NOT_ENOUGH);
                }
                item.setCartItemQuantity(newQuantity);
                item.setCreatedAt(new Date());
                BigDecimal additionalAmount = variantPrice.multiply(new BigDecimal(addQuantity));
                userCart.setCartTotal(userCart.getCartTotal().add(additionalAmount));

            } else {
                if (currentStock < addQuantity) {
                    log.warn("Not enough stock for variant ID: {}", variantId);
                    return ResponseHelper.badRequest(ResponseMessage.STOCK_NOT_ENOUGH);
                }

                CartItem newItem = cartItemService.createItemForCart(cart, userCart);
                newItem.setUnitPrice(variantPrice);
                userCart.getCartItems().add(newItem);

                BigDecimal itemTotal = variantPrice.multiply(new BigDecimal(addQuantity));
                userCart.setCartTotal(userCart.getCartTotal().add(itemTotal));
            }

            Cart savedCart = cartRepository.save(userCart);
            return ResponseHelper.ok(CartResponseDTO.fromEntity(savedCart, productService),
                    ResponseMessage.UPDATE_SUCCESS);

        } catch (Exception e) {
            log.error("Error adding product to cart for user: {}", principal.getName(), e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }
    @Override
    public TypeResponse<CartResponseDTO> getCartProduct(Principal principal, Pageable pageable) {
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            UUID cartId = user.getUserCart().getId();
            Optional<Cart> cartOpt = cartRepository.findById(cartId);
            if (cartOpt.isEmpty()) {
                log.error("Not enough stock for cart ID: {}", cartId);
                return ResponseHelper.notFound(ResponseMessage.CART_NOT_FOUND);
            }
            Cart cart = cartOpt.get();
            log.info("Fetched cart for user: {}", principal.getName());
            return ResponseHelper.ok(CartResponseDTO.fromEntity(cart, productService, pageable), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error("Error fetching cart for user: {}", principal.getName(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    @Transactional
    public TypeResponse<CartResponseDTO> removeProductFromCart(CartDTO cartDTO, Principal principal) {
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Cart cart = user.getUserCart();
            List<CartItem> cartItems = cart.getCartItems();

            if (cartItems.isEmpty()) {
                log.error("Cart is empty for user: {}", principal.getName());
                return ResponseHelper.notFound(ResponseMessage.CART_EMPTY);
            }

            Optional<CartItem> itemToUpdateOpt = cartItems.stream()
                    .filter(item -> item.getProductVariantId().equals(cartDTO.getVariantDTO().getId()))
                    .findFirst();

            if (itemToUpdateOpt.isEmpty()) {
                log.error("Product variant not found in cart for user: {}", principal.getName());
                return ResponseHelper.notFound(ResponseMessage.CART_EMPTY);
            }
            CartItem itemToUpdate = itemToUpdateOpt.get();
            int newQuantity = itemToUpdate.getCartItemQuantity() - cartDTO.getQuantity();

            if (newQuantity <= 0) {
                cart.getCartItems().remove(itemToUpdate);
                cart.setCartTotal(cart.getCartTotal().subtract(
                        itemToUpdate.getCartProduct().getPrice().multiply(
                                new BigDecimal(itemToUpdate.getCartItemQuantity()))));
            } else {
                itemToUpdate.setCartItemQuantity(newQuantity);
                cart.setCartTotal(cart.getCartTotal().subtract(
                        itemToUpdate.getCartProduct().getPrice().multiply(
                                new BigDecimal(cartDTO.getQuantity()))));
            }

            return ResponseHelper.ok(CartResponseDTO.fromEntity(cartRepository.save(cart), productService),
                    ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error removing product from cart for user: {}", principal.getName(), e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    public TypeResponse<Cart> changeQuantity(CartDTO cartDTO, Principal principal, int quantity) {
        return null;
    }

    @Override
    @Transactional
    public TypeResponse<CartResponseDTO> clearCart(Principal principal) {
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Cart cart = user.getUserCart();
            if (cart.getCartItems().isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.CART_EMPTY);
            }
            cart.getCartItems().clear();
            cart.setCartTotal(BigDecimal.ZERO);
            return ResponseHelper.ok(CartResponseDTO.fromEntity(cartRepository.save(cart), productService),
                    ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error("Error clearing cart for user: {}", principal.getName(), e);
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }
}
