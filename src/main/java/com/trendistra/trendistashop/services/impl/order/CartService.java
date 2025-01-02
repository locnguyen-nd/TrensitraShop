package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.dto.response.CartDTO;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.entities.user.CartItem;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.order.CartRepository;
import com.trendistra.trendistashop.services.ICartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Transactional
    public Cart addProductToCart(CartDTO cart, Principal principal) throws OrderCreationException {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Cart userCart = user.getUserCart();
        Optional<CartItem> existingItem = userCart.getCartItems().stream()
                .filter(item -> item.getProductVariantId().equals(cart.getVariantDTO().getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setCartItemQuantity(item.getCartItemQuantity() + cart.getQuantity());
            userCart.setCartTotal(userCart.getCartTotal()
                    .add(item.getCartProduct().getPrice()
                            .multiply(new BigDecimal(cart.getQuantity()))));
        } else {
            CartItem newItem = cartItemService.createItemForCart(cart, userCart);
            userCart.getCartItems().add(newItem);
            userCart.setCartTotal(userCart.getCartTotal()
                    .add(newItem.getCartProduct().getPrice()
                            .multiply(new BigDecimal(cart.getQuantity()))));
        }

        return cartRepository.save(userCart);
    }

    @Override
    public Cart getCartProduct(Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        UUID cartId = user.getUserCart().getId();
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            throw new ResourceNotFoundEx("Can't found card by id");
        }
        return cart.get();
    }

    @Override
    @Transactional
    public Cart removeProductFromCart(CartDTO cartDTO, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Cart cart = user.getUserCart();
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundEx("Cart is empty");
        }

        CartItem itemToUpdate = cartItems.stream()
                .filter(item -> item.getProductVariantId().equals(cartDTO.getVariantDTO().getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found in cart"));

        int newQuantity = itemToUpdate.getCartItemQuantity() - cartDTO.getQuantity();

        if (newQuantity <= 0) {
//            cartItems.remove(itemToUpdate);
            cart.getCartItems().remove(itemToUpdate);
            cart.setCartTotal(cart.getCartTotal().subtract(
                    itemToUpdate.getCartProduct().getPrice().multiply(
                            new BigDecimal(itemToUpdate.getCartItemQuantity())
                    )
            ));
        } else {
            itemToUpdate.setCartItemQuantity(newQuantity);
            cart.setCartTotal(cart.getCartTotal().subtract(
                    itemToUpdate.getCartProduct().getPrice().multiply(
                            new BigDecimal(cartDTO.getQuantity())
                    )
            ));
        }

        return cartRepository.save(cart);
    }


    @Override
    public Cart changeQuantity(CartDTO cartDTO, Principal principal, int quantity) {
        return null;
    }

    @Override
    public Cart clearCart(Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Cart cart = user.getUserCart();
        if (cart.getCartItems().size() == 0) {
            throw new ResourceNotFoundEx("Cart already empty");
        }
        List<CartItem> emptyCart = new ArrayList<>();
        user.getUserCart().setCartItems(emptyCart);
        user.getUserCart().setCartTotal(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }
}
