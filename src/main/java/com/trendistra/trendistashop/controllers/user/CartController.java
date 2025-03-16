package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.docs.cart.AddToCartDocs;
import com.trendistra.trendistashop.docs.cart.DeleteCartItemDocs;
import com.trendistra.trendistashop.docs.cart.GetCartDocs;
import com.trendistra.trendistashop.docs.cart.examples.CartRequestExamples;
import com.trendistra.trendistashop.docs.cart.ClearCartDocs;
import com.trendistra.trendistashop.dto.response.CartDTO;
import com.trendistra.trendistashop.dto.response.CartResponseDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.services.ICartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "${api.prefix}/cart")
@CrossOrigin
@Tag(name = "Cart")
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
    public ResponseEntity<TypeResponse<CartResponseDTO>> getCartProductHandler(Principal principal){
        TypeResponse<CartResponseDTO> cart = iCartService.getCartProduct(principal);
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
