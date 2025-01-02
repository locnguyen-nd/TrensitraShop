package com.trendistra.trendistashop.services;

import com.trendistra.trendistashop.dto.response.CartDTO;
import com.trendistra.trendistashop.entities.product.Product;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.entities.user.CartItem;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import org.springframework.security.core.parameters.P;

import java.security.Principal;

public interface ICartService {
    public Cart addProductToCart(CartDTO cart, Principal principal) throws OrderCreationException;
    public Cart getCartProduct (Principal principal);
    public Cart removeProductFromCart(CartDTO cartDTO, Principal principal);
    public  Cart changeQuantity(CartDTO cartDTO , Principal principal, int quantity);
    public Cart clearCart(Principal principal);
}
