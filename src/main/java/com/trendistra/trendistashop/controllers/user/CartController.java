package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.response.CartDTO;
import com.trendistra.trendistashop.dto.response.CartResponseDTO;
import com.trendistra.trendistashop.entities.user.Cart;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.repositories.order.CartRepository;
import com.trendistra.trendistashop.services.ICartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "${api.prefix}/cart")
@CrossOrigin
@Tag(name = "Cart")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @PostMapping(value = "/add")
    public ResponseEntity<CartResponseDTO> addProductToCart(@RequestBody CartDTO cartDTO, Principal principal) throws OrderCreationException {
        Cart cart = iCartService.addProductToCart(cartDTO , principal);
        return new ResponseEntity<>(CartResponseDTO.fromEntity(cart), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCartProductHandler(Principal principal){
        Cart cart = iCartService.getCartProduct(principal);
        return new ResponseEntity<>(CartResponseDTO.fromEntity(cart), HttpStatus.ACCEPTED);
    }


    @DeleteMapping(value = "/cart")
    public ResponseEntity<CartResponseDTO> removeProductFromCartHander(@RequestBody CartDTO cartdto , Principal principal){
        Cart cart = iCartService.removeProductFromCart(cartdto, principal);
        return new ResponseEntity<>(CartResponseDTO.fromEntity(cart),HttpStatus.OK);
    }

    @DeleteMapping(value = "/cart/clear")
    public ResponseEntity<CartResponseDTO> clearCartHandler(Principal principal){
        Cart cart = iCartService.clearCart(principal);
        return new ResponseEntity<>(CartResponseDTO.fromEntity(cart), HttpStatus.ACCEPTED);
    }

}
