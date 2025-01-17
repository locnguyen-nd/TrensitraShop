package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.dto.request.ProductRequestDTO;
import com.trendistra.trendistashop.dto.response.ProductDTO;
import com.trendistra.trendistashop.dto.response.ProductImageDTO;
import com.trendistra.trendistashop.dto.response.VariantDTO;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.product.*;
import com.trendistra.trendistashop.enums.DiscountType;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import com.trendistra.trendistashop.exceptions.InvalidParameterException;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.helper.GenerateCodeProduct;
import com.trendistra.trendistashop.helper.GenerateSlug;
import com.trendistra.trendistashop.repositories.category.CategoryRepository;
import com.trendistra.trendistashop.repositories.product.DiscountRepository;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import com.trendistra.trendistashop.services.IProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.trendistra.trendistashop.specifications.ProductSpecification.*;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private VariantService variantService;

    private GenerateSlug generateSlug;
    private GenerateCodeProduct generateCodeProduct;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ProductDTO> getAllProduct(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        if(productPage.isEmpty()) {
            throw new ResourceNotFoundEx("Products not found");
        }
        return productPage.map(this::mapToProductDto);
    }

    @Override
    public Page<ProductDTO> searchWithName(String name, Pageable pageable) {
        if (name == null) {
            throw new InvalidParameterException("You don't trying search with name and slug empty !");
        }
        Specification<Product> specification = Specification
                .where(hasName(name));
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        if (productPage.isEmpty()) {
            throw new ResourceNotFoundEx(String.format("Don't find any product with %s ", name));
        }
        return productPage.map(this::mapToProductDto);
    }

    @Override
    @Transactional
    public ProductDTO createProductWithImages(ProductRequestDTO productDto, List<MultipartFile> files) throws IOException {

        if (productRepository.existsByName(productDto.getName())) {
            throw new RuntimeException("Product with this name already exists");
        }
        // Fetch category
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));
        // Fetch discounts
        List<Discount> discounts = productDto.getDiscountIds() != null
                ? discountRepository.findAllById(productDto.getDiscountIds())
                : List.of();
        // Create Product entity
        Product product = Product.builder()
                .name(productDto.getName())
                .code(generateCodeProduct.generateCodeProduct()) // auto gen code
                .slug(generateSlug.generateSlug(productDto.getName())) // auto gen slug
                .status(productDto.getStatus() != null ? productDto.getStatus() : true)
                .originPrice(productDto.getOriginPrice())
                .price(productDto.getPrice())
                .isFreeShip(productDto.getIsFreeShip())
                .tag(productDto.getTag())
                .summary(productDto.getSummary())
                .description(productDto.getDescription())
                .views(0)
                .ratingAverage(0)
                .ratingTotal(0)
                .unitsSold(0)
                .category(category)
                .discounts(discounts)
                .build();
        // Save Product
        Product savedProduct = productRepository.save(product);

        List<ProductVariant> variants = variantService.createProductVariant(
                savedProduct,
                productDto.getVariants()
        );
        List<ProductImage> productImages = imageService.uploadImagesByColor(
                files,
                productDto.getColorImageMapping(),
                savedProduct.getId() // id saved
        );
        Optional<ProductImage> thumbnailImage = productImages.stream()
                .filter(image -> Boolean.TRUE.equals(image.getIsThumbnail()))
                .findFirst();
        Product addImage = productRepository.getReferenceById(savedProduct.getId());
        addImage.setFeaturedImage(thumbnailImage.get().getUrl());
        addImage.setImages(productImages);
        addImage.setProductVariants(variants);
        return mapToProductDto(addImage);
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx(String.format("Product not found with %s", id)));
        product.incrementView(); // tăng view xem sản phẩm
        return mapToProductDto(product);
    }
    @Override
    public Page<ProductDTO> getProductByTag(String genderSlug , String tag, Pageable pageable) {
        String tagEnum = tag.toUpperCase();
        Specification<Product> productSpecification = Specification
                .where(hasGenderSlug(genderSlug))
                .and(hasTag(ProductTagEnum.valueOf(tagEnum)));
        Page<Product> productPage = productRepository.findAll(productSpecification, pageable);
        if(productPage.isEmpty()) {
            throw new ResourceNotFoundEx(String.format("Don't find any product with %s and gender %s ", tag, genderSlug));
        }
        return productPage.map(this::mapToProductDto);
    }

    @Override
    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findProductsBySlug(slug);
        if(product == null) {
            throw new ResourceNotFoundEx(String.format("Product not found with %s ", slug));
        }
        return mapToProductDto(product);
    }

    @Override
    public Page<ProductDTO> filterProduct(String categorySlug, String genderSlug, String colorCode,
                                          String sizeValue, Double minPrice, Double maxPrice, PageRequest pageRequest) {
        // Nếu có cate thì kiểm tra xem có parent không nếu có thì search parent chính nó còn nếu kh search chính nó
        String parentSlug = null;
        if (categorySlug != null) {
            Optional<Category> category = categoryRepository.findBySlug(categorySlug);
            if (category.isPresent() && category.get().getParent() == null) {
                parentSlug = categorySlug;
                categorySlug = null;
            }
        }
        // Xây dựng Specification linh hoạt
        Specification<Product> productSpecification = Specification.where(null);

        if (categorySlug != null) {
            productSpecification = productSpecification.and(hasCategorySlug(categorySlug));
        }
        if (parentSlug != null) {
            productSpecification = productSpecification.and(hasParentCategorySlug(parentSlug));
        }
        if (genderSlug != null) {
            productSpecification = productSpecification.and(hasGenderSlug(genderSlug));
        }
        if (colorCode != null) {
            productSpecification = productSpecification.and(hasColorCode(colorCode));
        }
        if (sizeValue != null) {
            productSpecification = productSpecification.and(hasSizeValue(sizeValue));
        }
        if (minPrice != null && maxPrice != null) {
            productSpecification = productSpecification.and(hasPriceBetween(minPrice, maxPrice));
        }
        // Luôn lọc theo trạng thái (status = true)
        productSpecification = productSpecification.and(hasStatus(true));
        // Tìm kiếm sản phẩm theo điều kiện và phân trang
        Page<Product> productPage = productRepository.findAll(productSpecification, pageRequest);
        if(productPage.isEmpty()) {
            throw new ResourceNotFoundEx(String.format("Don't find any product with filter"));
        }
        // Chuyển đổi Page<Product> sang Page<ProductDTO>
        return productPage.map(this::mapToProductDto);
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productDto) {
        // Find existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update category if changed
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        // Update product details
        existingProduct.setName(productDto.getName());
        existingProduct.setCode(productDto.getCode());
        existingProduct.setSlug(generateSlug.generateSlug(productDto.getName()));
        existingProduct.setStatus(productDto.getStatus());
        existingProduct.setOriginPrice(productDto.getOriginPrice());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setIsFreeShip(productDto.getIsFreeShip());
        existingProduct.setCategory(category);
        // Save updated product
        Product updatedProduct = productRepository.save(existingProduct);

        return mapToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public void updateProductStatus(UUID id, boolean status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));

        product.setStatus(status);
        productRepository.save(product);
    }

    @Override
    public void updateProductQuantities(UUID id, int availableQuantities) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
        productRepository.save(product);
    }

    private BigDecimal getFinalPriceAfterDiscount(UUID productId) {
        BigDecimal discountValue = discountService.calculateFinalPriceAndUpdateProduct(productId);
        return discountValue;
    }

    // Helper method to map Product to ProductDto
    private ProductDTO mapToProductDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .urlImage(product.getFeaturedImage())
                .code(product.getCode())
                .slug(product.getSlug())
                .summary(product.getSummary())
                .description(product.getDescription())
                .status(product.getStatus())
                .discountValue(getFinalPriceAfterDiscount(product.getId()))
                .originPrice(product.getOriginPrice())
                .price(product.getPrice())
                .isFreeShip(product.getIsFreeShip())
                .availableQuantities(product.getProductVariants().stream()
                        .mapToInt(ProductVariant::getStockQuantity) // Lấy số lượng từ từng productVariant
                        .sum() // Tính tổng
                )
                .tag(product.getTag())
                .views(product.getViews())
                .ratingAverage(product.getRatingAverage())
                .ratingTotal(product.getRatingTotal())
                .unitsSold(product.getUnitsSold())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .productImages(product.getImages().stream()
                        .map(productImage -> covertImageToDTO(productImage))
                        .toList())
                .productVariants(product.getProductVariants().stream().map(
                        productVariant -> convertVariantDTO(productVariant)).toList())
                .build();
    }


    private ProductImageDTO covertImageToDTO(ProductImage productImage) {
        return ProductImageDTO.builder()
                .id(productImage.getId())
                .url(productImage.getUrl())
                .isThumbnail(productImage.getIsThumbnail())
                .productId(productImage.getProduct().getId())
                .colorId(productImage.getColor().getId())
                .build();
    }

    private VariantDTO convertVariantDTO(ProductVariant productVariant) {
        return VariantDTO.builder()
                .id(productVariant.getId())
                .codeVariant(productVariant.getCodeVariant())
                .colorCode(productVariant.getColor().getValue())
                .colorId(productVariant.getColor().getId())
                .sizeId(productVariant.getSize().getId())
                .sizeName(productVariant.getSize().getValue())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }
}
