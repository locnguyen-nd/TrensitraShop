package com.trendistashop.services.impl.order;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.response.CartDTO;
import com.trendistashop.dto.response.CartResponseDTO;
import com.trendistashop.dto.response.ProductDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.user.Cart;
import com.trendistashop.entities.user.CartItem;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.repositories.order.CartRepository;
import com.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistashop.services.ICartService;
import com.trendistashop.services.impl.product.ProductService;
import com.trendistashop.services.impl.product.VariantService;
import com.trendistashop.utils.ResponseHelper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public TypeResponse<CartResponseDTO> addProductToCart(CartDTO cart, Principal principal) {
        try {
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
            Cart userCart = user.getUserCart();
            Optional<CartItem> existingItem = userCart.getCartItems().stream()
                    .filter(item -> item.getProductVariantId().equals(cart.getVariantDTO().getId()))
                    .findFirst();


            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                int newQuantity = item.getCartItemQuantity() + cart.getQuantity();
                if (variantService.getStockForVariant(cart.getVariantDTO().getId()) < newQuantity) {
                    log.warn("Not enough stock for variant ID: {}", cart.getVariantDTO().getId());
                    return ResponseHelper.badRequest(ResponseMessage.STOCK_NOT_ENOUGH);
                }
                item.setCartItemQuantity(newQuantity);
                item.setCreatedAt(new Date());
                userCart.setCartTotal(userCart.getCartTotal()
                        .add(item.getCartProduct().getPrice()
                                .multiply(new BigDecimal(cart.getQuantity()))));
            } else {
                if (variantService.getStockForVariant(cart.getVariantDTO().getId()) < cart.getQuantity()) {
                    log.warn("Not enough stock for variant ID: {}", cart.getVariantDTO().getId());
                    return ResponseHelper.badRequest(ResponseMessage.STOCK_NOT_ENOUGH);
                }
                CartItem newItem = cartItemService.createItemForCart(cart, userCart);
                userCart.getCartItems().add(newItem);
                userCart.setCartTotal(userCart.getCartTotal()
                        .add(newItem.getCartProduct().getPrice()
                                .multiply(new BigDecimal(cart.getQuantity()))));
            }
            return ResponseHelper.ok(CartResponseDTO.fromEntity(cartRepository.save(userCart), productService),
                    ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error adding product to cart for user: {}", principal.getName(), e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    public TypeResponse<CartResponseDTO> getCartProduct(Principal principal) {
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
            return ResponseHelper.ok(CartResponseDTO.fromEntity(cart, productService), ResponseMessage.FETCH_SUCCESS);
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
            if (cart.getCartItems().size() == 0) {
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
