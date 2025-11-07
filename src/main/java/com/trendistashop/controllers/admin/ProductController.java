package com.trendistashop.controllers.admin;
import com.trendistashop.docs.example.ProductRequestExamples;
import com.trendistashop.docs.product.GetProductDocs;
import com.trendistashop.dto.request.ProductRequestDTO;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.ProductDTO;
import com.trendistashop.dto.response.SearchSuggestionDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.helper.PageConverter;
import com.trendistashop.services.IProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/products")
@CrossOrigin
@Tag(name = "Product API", description = "API quản lý sản phẩm")
public class ProductController {
    private final IProductService productService;
    private final PageConverter pageConvert;
    public ProductController(IProductService iProductService, PageConverter pageDTO) {
        this.productService = iProductService;
        this.pageConvert = pageDTO;
    }
    
    @Operation(summary = "Tạo sản phẩm")
    @PostMapping
    public ResponseEntity<TypeResponse<ProductDTO>> createProduct(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(schema = @Schema(implementation = ProductRequestDTO.class),
                        examples = {
                                @ExampleObject(
                                        name = "Create Product",
                                        summary = "Create product example",
                                        value = ProductRequestExamples.CREATE_PRODUCT_REQUEST
                                )
                        }
                )
        )
        @RequestBody ProductRequestDTO productRequestDTO) {
        TypeResponse<ProductDTO> createdProduct = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(createdProduct.getStatusCode()).body(createdProduct);
    }
    @Operation(summary = "Tìm kiếm sản phẩm theo tên")
    @GetMapping("/search")
    public ResponseEntity<TypeResponse<PageDTO<ProductDTO>>> getProductsByName(
            @RequestParam() String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        TypeResponse<Page<ProductDTO>> products = productService.searchWithName(name, pageable);
        PageDTO<ProductDTO> pageDTO = pageConvert.toPageDTO(products.getData());
        TypeResponse<PageDTO<ProductDTO>> response = new TypeResponse<>(
                true,
                products.getMessage(),
                products.getErrors(),
                pageDTO,
                products.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Gợi ý tìm kiếm sản phẩm")
    @GetMapping("/suggest")
    public ResponseEntity<TypeResponse<SearchSuggestionDTO>> getSuggestions(
            @RequestParam String keyword) {
        TypeResponse<SearchSuggestionDTO> products = productService.getSuggestion(keyword);
        return ResponseEntity.status(products.getStatusCode()).body(products);
    }

    @Operation(summary = "Lấy danh sách sản phẩm theo tag")
    @GetMapping("/tag")
    public ResponseEntity<TypeResponse<PageDTO<ProductDTO>>> getProductsByTag(
            @RequestParam String tag,
            @RequestParam String genderSlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        TypeResponse<Page<ProductDTO>> products = productService.getProductByTag(genderSlug, tag, pageable);
        PageDTO<ProductDTO> pageDTO = pageConvert.toPageDTO(products.getData());
        TypeResponse<PageDTO<ProductDTO>> response = new TypeResponse<>(
                true,
                products.getMessage(),
                products.getErrors(),
                pageDTO,
                products.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Lấy sản phẩm theo slug")
    @GetProductDocs
    @GetMapping("/slug/{slug}")
    public ResponseEntity <TypeResponse<ProductDTO>> getProductsBySlug(
            @PathVariable String slug
    ) {
        TypeResponse<ProductDTO> product = productService.getProductBySlug(slug);
        return ResponseEntity.status(product.getStatusCode()).body(product);
    }

    @Operation(summary = "Lọc sản phẩm")
    @GetMapping
    public ResponseEntity<TypeResponse<PageDTO<ProductDTO>>> getAllProductsWithFilter(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String categorySlug,
            @RequestParam(required = false) String colorCode,
            @RequestParam(required = false) String sizeValue,
            @RequestParam(required = false) String genderSlug,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending
    ) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy , ascending);
        TypeResponse<Page<ProductDTO>> products =   productService.filterProduct(keyword, tag, categorySlug, genderSlug, colorCode, sizeValue, minPrice, maxPrice, status, pageRequest);
        PageDTO<ProductDTO> pageDTO = pageConvert.toPageDTO(products.getData());
        TypeResponse<PageDTO<ProductDTO>> response = new TypeResponse<>(
                true,
                products.getMessage(),
                products.getErrors(),
                pageDTO,
                products.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    private PageRequest createPageRequest (int page , int size, String sortBy , Boolean ascending) {
        if(sortBy == null  && ascending) {
            return PageRequest.of(page,size);
        } else {
            Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending() ;
            return  PageRequest.of(page, size , sort);
        }
    }

    @Operation(summary = "Lấy sản phẩm theo id")
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse<ProductDTO>> getProductById(@PathVariable UUID id) {
        TypeResponse<ProductDTO> product = productService.getProductById(id);
        return ResponseEntity.status(product.getStatusCode()).body(product);
    }

    @Operation(summary = "Cập nhật sản phẩm")
    @PutMapping("/{id}")
    public ResponseEntity<TypeResponse<ProductDTO>> updateProduct(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Update Product",
                                    summary = "Update product example",
                                    value = ProductRequestExamples.UPDATE_PRODUCT_REQUEST
                            )
                    }
            )
    )
    @PathVariable UUID id,
    @RequestBody ProductRequestDTO productDto
    ) {
        TypeResponse<ProductDTO> updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.status(updatedProduct.getStatusCode()).body(updatedProduct);
    }
    
    @Operation(summary = "Xóa sản phẩm")
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResponse<Void>> deleteProduct(@PathVariable UUID id) {
        TypeResponse<Void> status = productService.deleteProduct(id);
        return ResponseEntity.status(status.getStatusCode()).body(status);
    }
}
