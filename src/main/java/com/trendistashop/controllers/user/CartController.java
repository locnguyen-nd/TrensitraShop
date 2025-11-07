package com.trendistashop.controllers.user;

import com.trendistashop.docs.cart.AddToCartDocs;
import com.trendistashop.docs.cart.ClearCartDocs;
import com.trendistashop.docs.cart.DeleteCartItemDocs;
import com.trendistashop.docs.cart.GetCartDocs;
import com.trendistashop.docs.cart.examples.CartRequestExamples;
import com.trendistashop.dto.response.CartDTO;
import com.trendistashop.dto.response.CartResponseDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.services.ICartService;
import com.trendistashop.exceptions.OrderCreationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "${api.prefix}/cart")
@CrossOrigin
@Tag(name = "Cart API", description = "API quản lý giỏ hàng")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @Operation(summary = "Thêm sản phẩm vào giỏ hàng")
    @AddToCartDocs
    @PostMapping(value = "/add")
    public ResponseEntity<TypeResponse<CartResponseDTO>> addProductToCart(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = CartDTO.class),
                examples = @ExampleObject(value = CartRequestExamples.ADD_TO_CART_REQUEST)
            )
        )
        @RequestBody CartDTO cartDTO,
        Principal principal
    ) throws OrderCreationException {
        TypeResponse<CartResponseDTO> cart = iCartService.addProductToCart(cartDTO , principal);
        return ResponseEntity.status(cart.getStatusCode()).body(cart);
    }

    @Operation(summary = "Lấy danh sách sản phẩm trong giỏ hàng")
    @GetCartDocs
    @GetMapping
    public ResponseEntity<TypeResponse<CartResponseDTO>> getCart(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        Pageable pageable = PageRequest.of(page, size);
        TypeResponse<CartResponseDTO> cart = iCartService.getCartProduct(principal, pageable);
        return ResponseEntity.status(cart.getStatusCode()).body(cart);
    }

    @Operation(summary = "Xóa sản phẩm khỏi giỏ hàng")
    @DeleteCartItemDocs
    @DeleteMapping(value = "/delete")
    public ResponseEntity<TypeResponse<CartResponseDTO>> removeProductFromCartHander(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = CartDTO.class),
                examples = @ExampleObject(value = CartRequestExamples.DELETE_CART_ITEM_REQUEST)
            )
        )
        @RequestBody CartDTO cartdto,
        Principal principal
    ){
        TypeResponse<CartResponseDTO> cart = iCartService.removeProductFromCart(cartdto, principal);
        return ResponseEntity.status(cart.getStatusCode()).body(cart);
    }

    @Operation(summary = "Xóa tất cả sản phẩm khỏi giỏ hàng")
    @ClearCartDocs
    @DeleteMapping(value = "/clear")
    public ResponseEntity<TypeResponse<CartResponseDTO>> clearCartHandler(Principal principal){
        TypeResponse<CartResponseDTO> cart = iCartService.clearCart(principal);
        return ResponseEntity.status(cart.getStatusCode()).body(cart);
    }

}
