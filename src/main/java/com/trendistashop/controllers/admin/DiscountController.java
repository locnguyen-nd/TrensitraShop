package com.trendistashop.controllers.admin;

import com.trendistashop.docs.auth.examples.AuthRequestExamples;
import com.trendistashop.docs.example.DiscountRequestExamples;
import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.dto.request.RegisterRequest;
import com.trendistashop.dto.response.DiscountApply;
import com.trendistashop.dto.response.DiscountDTO;
import com.trendistashop.services.impl.product.DiscountService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/discounts")
@CrossOrigin
@Tag(name = "Discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    // Create
    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DiscountRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Create Order Discount",
                                            summary = "Create discount applied to order",
                                            value = DiscountRequestExamples.CREATE_DISCOUNT_ORDER_REQUEST
                                    ),
                                    @ExampleObject(
                                            name = "Create Product Discount",
                                            summary = "Create discount applied to product",
                                            value = DiscountRequestExamples.CREATE_DISCOUNT_PRODUCT_REQUEST
                                    )
                            }
                    )
            )
            @RequestBody DiscountRequest discountDto,
            @RequestParam(value = "categoryIds",required = false) List<UUID> categoryIds,
            @RequestParam(value = "productIds", required = false) List<UUID> productIds
    )  {
        DiscountDTO createdDiscount = discountService
                .createDiscount(discountDto, categoryIds, productIds);
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    @PutMapping(value = "/apply")
    public ResponseEntity<?> applyDiscount(
            @RequestParam String discountCode,
            @RequestParam UUID orderId
            ) {
        DiscountApply discountDTO = discountService.applyDiscountToOrder(discountCode, orderId);
        // Check if discount was successfully applied
        if(discountDTO == null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Discount can not apply for this order");
        }
        return new ResponseEntity<>(discountDTO, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity <?> getAllDiscount(){
        List<DiscountDTO> discountDTOS = discountService.getAllDiscount();
        if(discountDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No have discount");
        }
        return new ResponseEntity<>(discountDTOS, HttpStatus.OK);
    }
    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable UUID id) {
        DiscountDTO discount = discountService.getDiscountById(id);
        return ResponseEntity.ok(discount);
    }

    // Update
    @PutMapping(value = "/{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable("id") UUID id,
                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                              required = true,
                                                              content = @Content(schema = @Schema(implementation = DiscountRequest.class),
                                                                      examples = {
                                                                              @ExampleObject(
                                                                                      name = "Update Order Discount",
                                                                                      summary = "Update discount applied to order",
                                                                                      value = DiscountRequestExamples.UPDATE_DISCOUNT_ORDER_REQUEST
                                                                              ),
                                                                              @ExampleObject(
                                                                                      name = "Update Product Discount",
                                                                                      summary = "Update discount applied to product",
                                                                                      value = DiscountRequestExamples.UPDATE_DISCOUNT_PRODUCT_REQUEST
                                                                              )
                                                                      }
                                                              )
                                                      )
                                                      @RequestBody @Valid DiscountRequest discountDto,
                                                      @RequestParam(value = "categoryIds", required = false) List<UUID> categoryIds,
                                                      @RequestParam(value = "productIds", required = false) List<UUID> productIds){
        return new ResponseEntity<>(discountService.updateDiscount(id, discountDto, categoryIds, productIds), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable UUID id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }

    // Toggle Discount Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleDiscountStatus(
            @PathVariable UUID id,
            @RequestParam boolean status
    ) {
        discountService.setDiscountStatus(id, status);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/create-product/apply")
    public ResponseEntity<Map<String,BigDecimal>> applyDiscountToNewProduct(
            @RequestParam String discountCode,
            @RequestParam BigDecimal price
    ) {
        BigDecimal priceAfterDiscount = discountService.applyDiscountToNewProduct(discountCode, price);
        if(priceAfterDiscount == null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Map<String, BigDecimal> discountDTO = Map.of("priceAfterDiscount", priceAfterDiscount);
        return new ResponseEntity<>(discountDTO, HttpStatus.OK);
    }
}
