package com.trendistashop.controllers.admin;

import com.trendistashop.docs.example.DiscountRequestExamples;
import com.trendistashop.dto.request.DiscountRequest;
import com.trendistashop.dto.response.*;
import com.trendistashop.enums.DiscountApplyFor;
import com.trendistashop.enums.DiscountType;
import com.trendistashop.helper.PageConverter;
import com.trendistashop.services.impl.product.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/discounts")
@CrossOrigin
@Tag(name = "Discount API", description = "API quản lý mã khuyến mãi")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private PageConverter pageConverter;

    // Create
    @PostMapping
    @Operation(summary = "Tạo discount mới cho ứng dụng có 3 loại (sản phẩm / shipping / order)")
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
                                    ),
                                    @ExampleObject(
                                            name = "Create Shipping Discount",
                                            summary = "Create discount applied to shipping",
                                            value = DiscountRequestExamples.CREATE_DISCOUNT_SHIPPING_REQUEST
                                    )
                            }
                    )
            )
            @RequestBody DiscountRequest discountDto,
            @RequestParam(value = "categoryIds", required = false) List<UUID> categoryIds,
            @RequestParam(value = "productIds", required = false) List<UUID> productIds
    ) {
        TypeResponse<DiscountDTO> createdDiscount = discountService
                .createDiscount(discountDto, categoryIds, productIds);
        return ResponseEntity.status(createdDiscount.getStatusCode()).body(createdDiscount);
    }

    @GetMapping
    public ResponseEntity<TypeResponse<PageDTO<DiscountDTO>>> getAllDiscount(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) DiscountType discountType,
            @RequestParam(required = false) DiscountApplyFor discountApplyFor,
            @RequestParam(required = false) BigDecimal discountValue,
            @RequestParam(required = false) BigDecimal maxDiscountValue,
            @RequestParam(required = false) BigDecimal maxDiscountValueFrom,
            @RequestParam(required = false) BigDecimal maxDiscountValueTo,
            @RequestParam(required = false) BigDecimal minOrderValue,
            @RequestParam(required = false) BigDecimal minOrderValueFrom,
            @RequestParam(required = false) BigDecimal minOrderValueTo,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime endDate,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending
    ) {
        String validSortBy = validateSortBy(sortBy);

        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setCode(code);
        discountRequest.setDiscountType(discountType);
        discountRequest.setDiscountApplyFor(discountApplyFor);
        discountRequest.setDiscountValue(discountValue);
        discountRequest.setMaxDiscountValue(maxDiscountValue);
        discountRequest.setMaxDiscountValueFrom(maxDiscountValueFrom);
        discountRequest.setMaxDiscountValueTo(maxDiscountValueTo);
        discountRequest.setMinOrderValue(minOrderValue);
        discountRequest.setMinOrderValueFrom(minOrderValueFrom);
        discountRequest.setMinOrderValueTo(minOrderValueTo);
        discountRequest.setStartDate(startDate);
        discountRequest.setEndDate(endDate);
        discountRequest.setIsActive(isActive);
        PageRequest pageRequest = PageRequest.of(page, size, ascending ? Sort.by(validSortBy).ascending() : Sort.by(validSortBy).descending());

        TypeResponse<Page<DiscountDTO>> discounts = discountService.getAllDiscount(discountRequest, pageRequest);
        PageDTO<DiscountDTO> pageDTO = pageConverter.toPageDTO(discounts.getData());
        TypeResponse<PageDTO<DiscountDTO>> response = new TypeResponse<>(
                true,
                discounts.getMessage(),
                discounts.getErrors(),
                pageDTO,
                discounts.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private String validateSortBy(String sortBy) {
        String[] validFields = {"id", "code", "discountType", "discountApply", "discountValue", "maxDiscountValue", "minOrderValue", "startDate", "endDate", "isActive", "createdAt", "updatedAt"};
        for (String field : validFields) {
            if (field.equalsIgnoreCase(sortBy)) {
                return field;
            }
        }
        return "createdAt";
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
        @RequestParam(value = "productIds", required = false) List<UUID> productIds) {
        TypeResponse<DiscountDTO> updatedDiscount = discountService.updateDiscount(id, discountDto, categoryIds, productIds);
        return ResponseEntity.status(updatedDiscount.getStatusCode()).body(updatedDiscount);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteDiscount(@PathVariable UUID id) {
        TypeResponse<Void> response = discountService.deleteDiscount(id);
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
}
