package com.trendistra.trendistashop.controllers.admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistra.trendistashop.dto.request.ProductRequestDTO;
import com.trendistra.trendistashop.dto.response.ProductDTO;
import com.trendistra.trendistashop.dto.response.ProductImageDTO;
import com.trendistra.trendistashop.services.IProductService;
import com.trendistra.trendistashop.services.impl.product.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/products")
@CrossOrigin
@Tag(name = "Products")
public class ProductController {
    private final IProductService productService;
    public ProductController(IProductService iProductService) {
        this.productService = iProductService;
    }
    // Create Product
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@RequestPart("productRequest") String productRequestJson,
                                                    @RequestPart("images") List<MultipartFile> files) throws IOException {
        // Deserialize ProductDTO from JSON
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDTO productRequestDTO = objectMapper.readValue(productRequestJson, ProductRequestDTO.class);
        ProductDTO createdProduct = productService.createProductWithImages(productRequestDTO, files);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    // Read All Products
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.getAllProduct(pageable);
        return ResponseEntity.ok(products);
    }
    @GetMapping("search")
    public ResponseEntity<Page<ProductDTO>> getProductsByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.searchWithName(name, pageable);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/tag/{tag}")
    public ResponseEntity<Page<ProductDTO>> getProductsByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.getProductByTag(tag, pageable);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/slug/{slug}")
    public ResponseEntity <ProductDTO> getProductsBySlug(
            @PathVariable String slug
    ) {
        ProductDTO product = productService.getProductBySlug(slug);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/filter")
    public Page<ProductDTO> getAllProductsWithFilter(
            @RequestParam() UUID categoryId,
            @RequestParam(required = false) UUID colorId,
            @RequestParam(required = false) UUID sizeId,
            @RequestParam(required = false) UUID genderId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "price") String sortBy,// price // updateAt
            @RequestParam(defaultValue = "false") boolean ascending
    ) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy , ascending);
        Page<ProductDTO>products =   productService.filterProduct(categoryId, genderId, colorId, sizeId, minPrice, maxPrice, pageRequest);
        return products;
    }
    private PageRequest createPageRequest (int page , int size, String sortBy , Boolean ascending) {
        if(sortBy == null  && ascending.booleanValue() == true) {
            return PageRequest.of(page,size);
        } else {
            Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending() ;
            return  PageRequest.of(page, size , sort);
        }
    }
    // Read Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductDTO productDto
    ) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }
    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Update Product Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateProductStatus(
            @PathVariable UUID id,
            @RequestParam boolean status
    ) {
        productService.updateProductStatus(id, status);
        return ResponseEntity.ok().build();
    }

    // Update Product Quantities
    @PatchMapping("/{id}/quantities")
    public ResponseEntity<Void> updateProductQuantities(
            @PathVariable UUID id,
            @RequestParam int availableQuantities
    ) {
        productService.updateProductQuantities(id, availableQuantities);
        return ResponseEntity.ok().build();
    }
}
