package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.request.OrderRequest;
import com.trendistra.trendistashop.dto.response.OrderDetailDTO;
import com.trendistra.trendistashop.entities.user.Order;
import com.trendistra.trendistashop.exceptions.OrderCreationException;
import com.trendistra.trendistashop.services.impl.order.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/card-order")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Order")
public class OrderController {
    private final  OrderService orderService;

    @GetMapping("/user")
    public ResponseEntity<List<OrderDetailDTO>> getOrderUser (Principal principal) {
        List<OrderDetailDTO> orders = orderService.getOrderByUser(principal.getName());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder (@RequestBody OrderRequest orderRequest , Principal principal) throws OrderCreationException {
        Order order = orderService.createOrder(orderRequest, principal);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID id, Principal principal){
        orderService.cancelOrder(id,principal);
        return ResponseEntity.ok("Your order has been canceled successfully.");
    }
}
