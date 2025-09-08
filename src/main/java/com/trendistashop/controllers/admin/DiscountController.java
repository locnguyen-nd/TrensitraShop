package com.trendistashop.controllers.admin;
import com.trendistashop.docs.example.DiscountRequestExamples;
import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.dto.response.DiscountApply;
import com.trendistashop.dto.response.DiscountDTO;
import com.trendistashop.dto.response.TypeResponse;
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
    public ResponseEntity<TypeResponse<DiscountDTO>> createDiscount(
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
        TypeResponse<DiscountDTO> createdDiscount = discountService
                .createDiscount(discountDto, categoryIds, productIds);
        return ResponseEntity.status(createdDiscount.getStatusCode()).body(createdDiscount);
    }

    @PutMapping(value = "/apply")
    public ResponseEntity<TypeResponse<DiscountApply>> applyDiscount(
            @RequestParam String discountCode,
            @RequestParam UUID orderId) {
        TypeResponse<DiscountApply> discountDTO = discountService.applyDiscountToOrder(discountCode, orderId);
        return ResponseEntity.status(discountDTO.getStatusCode()).body(discountDTO);
    }
    @GetMapping
    public ResponseEntity<TypeResponse<List<DiscountDTO>>> getAllDiscount(){
        TypeResponse<List<DiscountDTO>> discountDTOS = discountService.getAllDiscount();
        return ResponseEntity.status(discountDTOS.getStatusCode()).body(discountDTOS);
    }
    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse<DiscountDTO>> getDiscountById(@PathVariable UUID id) {
        TypeResponse<DiscountDTO> discount = discountService.getDiscountById(id);
        return ResponseEntity.status(discount.getStatusCode()).body(discount);
    }

    // Update
    @PutMapping(value = "/{id}")
    public ResponseEntity<TypeResponse<DiscountDTO>> updateDiscount(@PathVariable("id") UUID id,
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
        TypeResponse<DiscountDTO> updatedDiscount = discountService.updateDiscount(id, discountDto, categoryIds, productIds);
        return ResponseEntity.status(updatedDiscount.getStatusCode()).body(updatedDiscount);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteDiscount(@PathVariable UUID id) {
        TypeResponse<Void>  response =  discountService.deleteDiscount(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Toggle Discount Status
    @PutMapping("/{id}/status")
    public ResponseEntity<TypeResponse<Void>> toggleDiscountStatus(
            @PathVariable UUID id,
            @RequestParam boolean status
    ) {
        TypeResponse<Void> response = discountService.setDiscountStatus(id, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping("/create-product/apply")
    public ResponseEntity<Map<String,BigDecimal>> applyDiscountToNewProduct(
            @RequestParam String discountCode,
            @RequestParam BigDecimal price
    ) {
        BigDecimal priceAfterDiscount = discountService.applyDiscountToNewProduct(discountCode, price).getData();
        if(priceAfterDiscount == null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Map<String, BigDecimal> discount = Map.of("priceAfter", priceAfterDiscount);
        return ResponseEntity.ok(discount);
    }
}
