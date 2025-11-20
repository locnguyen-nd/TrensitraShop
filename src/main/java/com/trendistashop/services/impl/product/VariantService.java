package com.trendistashop.services.impl.product;

import com.trendistashop.dto.request.VariantRequestDTO;
import com.trendistashop.entities.product.Color;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.product.Size;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.repositories.product.ColorRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistashop.repositories.product.SizeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductVariant> createProductVariant(Product product, List<VariantRequestDTO> variantRequests) {
        List<ProductVariant> variants = variantRequests.stream().map(variantRequest -> {
            Color color = colorRepository.findById(variantRequest.getColorId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Color not found"));
            Size size = sizeRepository.findById(variantRequest.getSizeId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Size not found"));
            String codeName = product.getCode() + "-" + color.getName() + "-" + size.getValue();
            BigDecimal price = (variantRequest.getPrice() != null
                    && variantRequest.getPrice().compareTo(BigDecimal.ZERO) > 0)
                    ? variantRequest.getPrice()
                    : product.getOriginPrice();
            return ProductVariant.builder()
                    .product(product)
                    .color(color)
                    .size(size)
                    .codeVariant(codeName.toUpperCase())
                    .order(variantRequest.getOrder())
                    .price(price)
                    .originPrice(price)
                    .stockQuantity(variantRequest.getStockQuantity())
                    .build();
        }).collect(Collectors.toList());
        return productVariantRepository.saveAll(variants);
    }
    @Transactional
    public List<ProductVariant> updateVariant(Product managedProduct, List<VariantRequestDTO> variantRequests) {
        // Lấy danh sách ProductVariant hiện có
        List<ProductVariant> existingVariants = productVariantRepository.findByProductId(managedProduct.getId());
        // Tạo map để tra cứu ProductVariant hiện có theo colorId và sizeId
        Map<String, ProductVariant> existingVariantMap = existingVariants.stream()
                .collect(Collectors.toMap(
                        variant -> variant.getColor().getId() + "-" + variant.getSize().getId(),
                        variant -> variant
                ));

        // Chuẩn bị danh sách ProductVariant để lưu
        List<ProductVariant> variantsToSave = new ArrayList<>();
        List<ProductVariant> variantsToDelete = new ArrayList<>(existingVariants);

        if (variantRequests != null && !variantRequests.isEmpty()) {
            for (VariantRequestDTO variantRequest : variantRequests) {
                Color color = colorRepository.findById(variantRequest.getColorId())
                        .orElseThrow(() -> new ResourceNotFoundEx("Color not found"));
                Size size = sizeRepository.findById(variantRequest.getSizeId())
                        .orElseThrow(() -> new ResourceNotFoundEx("Size not found"));
                String codeName = managedProduct.getCode() + "-" + color.getName() + "-" + size.getValue();
                String variantKey = variantRequest.getColorId() + "-" + variantRequest.getSizeId();
                ProductVariant existingVariant = existingVariantMap.get(variantKey);
                if (existingVariant != null) {
                    existingVariant.setColor(color);
                    existingVariant.setSize(size);
                    existingVariant.setCodeVariant(codeName.toUpperCase());
                    if (variantRequest.getPrice() != null
                            && variantRequest.getPrice().compareTo(BigDecimal.ZERO) > 0
                            && variantRequest.getPrice().compareTo(managedProduct.getPrice()) != 0) {
                        existingVariant.setOriginPrice(variantRequest.getPrice());
                        existingVariant.setPrice(variantRequest.getPrice());
                    } else {
                        existingVariant.setOriginPrice(managedProduct.getOriginPrice());
                        existingVariant.setPrice(managedProduct.getOriginPrice());
                    }
                    existingVariant.setOrder(variantRequest.getOrder());
                    existingVariant.setStockQuantity(variantRequest.getStockQuantity());
                    variantsToSave.add(existingVariant);
                    variantsToDelete.remove(existingVariant);
                    log.info("Cập nhật variant colorId: {}, sizeId: {}", variantRequest.getColorId(), variantRequest.getSizeId());
                } else {
                    BigDecimal originPrice = (variantRequest.getPrice() != null && variantRequest.getPrice().compareTo(BigDecimal.ZERO) > 0)
                            ? variantRequest.getPrice()
                            : managedProduct.getOriginPrice();
                    ProductVariant newVariant = ProductVariant.builder()
                            .product(managedProduct)
                            .color(color)
                            .size(size)
                            .codeVariant(codeName.toUpperCase())
                            .originPrice(originPrice)
                            .price(originPrice)
                            .stockQuantity(variantRequest.getStockQuantity())
                            .order(variantRequest.getOrder())
                            .build();
                    variantsToSave.add(newVariant);
                    log.info("Tạo mới variant colorId: {}, sizeId: {}", variantRequest.getColorId(), variantRequest.getSizeId());
                }
            }
        }
        // Xóa các ProductVariant không còn trong request
        if (!variantsToDelete.isEmpty()) {
            productVariantRepository.deleteAll(variantsToDelete);
            log.info("Xóa {} variant không còn trong request", variantsToDelete.size());
        }
        // Lưu các ProductVariant (mới và cập nhật)
        List<ProductVariant> savedVariants = productVariantRepository.saveAll(variantsToSave);
        return savedVariants;
    }
    public int getStockForVariant(UUID variantId) {
        Integer stock = productVariantRepository.findStockById(variantId);
        return stock != null ? stock : 0;
    }
    public ProductVariant getVariantById(UUID variantId) {
        log.info("Get variant by id {}", variantId);
        return productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundEx("Product variant not found"));
    }
}
