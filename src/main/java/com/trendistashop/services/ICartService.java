package com.trendistashop.services;

import com.trendistashop.dto.response.CartDTO;
import com.trendistashop.dto.response.CartResponseDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Cart;

import java.security.Principal;

public interface ICartService {
    public TypeResponse<CartResponseDTO> addProductToCart(CartDTO cart, Principal principal) ;
    public TypeResponse<CartResponseDTO> getCartProduct (Principal principal);
    public TypeResponse<CartResponseDTO> removeProductFromCart(CartDTO cartDTO, Principal principal);
    public TypeResponse<Cart> changeQuantity(CartDTO cartDTO , Principal principal, int quantity);
    public TypeResponse<CartResponseDTO> clearCart(Principal principal);
}
