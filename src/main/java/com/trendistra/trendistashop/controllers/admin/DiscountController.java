package com.trendistra.trendistashop.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.trendistra.trendistashop.dto.response.DiscountDTO;
import com.trendistra.trendistashop.services.impl.product.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/discounts")
@CrossOrigin
@Tag(name = "Discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    // Create
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiscountDTO> createDiscount(
            @RequestPart("discount") String discountDtoJson,
            @RequestParam(value = "categoryIds",required = false) List<UUID> categoryIds,
            @RequestParam(value = "productIds", required = false) List<UUID> productIds,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        // Parse JSON string to DTO
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DiscountDTO discountDto = objectMapper.readValue(discountDtoJson, DiscountDTO.class);
        DiscountDTO createdDiscount = discountService
                .createDiscount(discountDto, categoryIds, productIds, imageFile);
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    @PostMapping(value = "/apply")
    public ResponseEntity<?> applyDiscount(
            @RequestParam UUID discountId,
            @RequestParam BigDecimal orderTotal
            ) {
        DiscountDTO discountDTO = discountService.applyDiscountToOrder(discountId, orderTotal);
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No have dicount");
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
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable("id") UUID id,
                                                      @RequestPart("discount") String discountDto,
                                                      @RequestParam(value = "categoryIds", required = false) List<UUID> categoryIds,
                                                      @RequestParam(value = "productIds", required = false) List<UUID> productIds,
                                                      @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DiscountDTO discount = objectMapper.readValue(discountDto, DiscountDTO.class);
        return new ResponseEntity<>(discountService.updateDiscount(id, discount, categoryIds, productIds, imageFile), HttpStatus.OK);
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
}
