package com.trendistashop.services.impl.product;

import com.trendistashop.dto.request.VariantRequestDTO;
import com.trendistashop.entities.product.Color;
import com.trendistashop.entities.product.Product;
import com.trendistashop.entities.product.ProductVariant;
import com.trendistashop.entities.product.Size;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.repositories.product.ColorRepository;
import com.trendistashop.repositories.product.ProductVariantRepository;
import com.trendistashop.repositories.product.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeRepository sizeRepository;

    public List<ProductVariant> createProductVariant(Product product, List<VariantRequestDTO> variantRequests) {
        List<ProductVariant> variants = variantRequests.stream().map(variantRequest -> {
            Color color = colorRepository.findById(variantRequest.getColorId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Color not found"));
            Size size = sizeRepository.findById(variantRequest.getSizeId())
                    .orElseThrow(() -> new ResourceNotFoundEx("Size not found"));
            String codeName = product.getCode() + "-" + color.getName() + "-" + size.getValue();
            BigDecimal price = variantRequest.getPrice() != null ? variantRequest.getPrice() : product.getPrice();
            return ProductVariant.builder()
                    .product(product)
                    .color(color)
                    .size(size)
                    .codeVariant(codeName.toUpperCase())
                    .price(price)
                    .stockQuantity(variantRequest.getStockQuantity())
                    .build();
        }).collect(Collectors.toList());
        return productVariantRepository.saveAll(variants);
    }
}
