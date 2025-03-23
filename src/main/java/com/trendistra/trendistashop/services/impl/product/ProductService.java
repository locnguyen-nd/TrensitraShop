package com.trendistra.trendistashop.services.impl.product;

import com.trendistra.trendistashop.Util.ResponseHelper;
import com.trendistra.trendistashop.dto.request.ProductRequestDTO;
import com.trendistra.trendistashop.dto.response.ProductDTO;
import com.trendistra.trendistashop.dto.response.ProductImageDTO;
import com.trendistra.trendistashop.dto.response.SearchSuggestionDTO;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.dto.response.VariantDTO;
import com.trendistra.trendistashop.entities.category.Category;
import com.trendistra.trendistashop.entities.product.*;
import com.trendistra.trendistashop.enums.ProductTagEnum;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.helper.GenerateCodeProduct;
import com.trendistra.trendistashop.helper.GenerateSlug;
import com.trendistra.trendistashop.repositories.category.CategoryRepository;
import com.trendistra.trendistashop.repositories.product.DiscountRepository;
import com.trendistra.trendistashop.repositories.product.ProductRepository;
import com.trendistra.trendistashop.services.CloudinaryService;
import com.trendistra.trendistashop.services.IProductService;
import com.trendistra.trendistashop.specifications.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
@Transactional
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final DiscountService discountService;
    private final ImageService imageService;
    private final VariantService variantService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final int suggestionLimit;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, DiscountRepository discountRepository, DiscountService discountService, ImageService imageService, VariantService variantService, CloudinaryService cloudinaryService, ModelMapper modelMapper,   @Value("${search.suggestion.limit}") int suggestionLimit) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.discountRepository = discountRepository;
        this.discountService = discountService;
        this.imageService = imageService;
        this.variantService = variantService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.suggestionLimit = suggestionLimit;
    }

    @Override
    public TypeResponse<Page<ProductDTO>> getAllProduct(Pageable pageable) {
       try {
            Page<Product> productPage = productRepository.findAll(pageable);
            if(productPage.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy sản phẩm nào");
            }
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), "Lấy danh sách sản phẩm thành công");
       } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi lấy danh sách sản phẩm");
       }
    }

    @Override
    public TypeResponse<SearchSuggestionDTO> getSuggestion (String keyword) {
        try {
            if(keyword == null || keyword.trim().isEmpty()) {
                return ResponseHelper.ok(new SearchSuggestionDTO(), "Lấy danh sách sản phẩm thành công");
            }
            List<Product> products = productRepository.findProductNames(
                    keyword.toLowerCase().trim(),
                    PageRequest.of(0, suggestionLimit)
            );
            List<String> categorySlugs = categoryRepository.findCategorySlugs(
                    keyword.toLowerCase().trim(),
                    PageRequest.of(0, suggestionLimit)
            );
            SearchSuggestionDTO result = new SearchSuggestionDTO();
            result.setProductNames(products.stream()
                    .map(Product::getName)
                    .collect(Collectors.toList()));
            result.setProducts(products.stream()
                    .map(this::mapToProductDto)
                    .collect(Collectors.toList()));
            result.setCategorySlug(categorySlugs);
            return ResponseHelper.ok(result, "Lấy danh sách sản phẩm thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi lấy danh sách sản phẩm thành công");
        }
    }

    @Override
    public TypeResponse<Page<ProductDTO>> searchWithName(String keyword, Pageable pageable) {
        try {
            if (keyword == null) {
                return ResponseHelper.badRequest("Tên sản phẩm không được để trống!");
            }
            Specification<Product> specification = Specification
                    .where(hasName(keyword));
            Page<Product> productPage = productRepository.findAll(specification, pageable);
            if (productPage.isEmpty()) {
                return ResponseHelper.notFound(String.format("Không tìm thấy sản phẩm với tên %s ", keyword));
            }
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), "Lấy danh sách sản phẩm thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi tìm kiếm sản phẩm theo tên");
        }
    }

    @Override
    @Transactional
    public ProductDTO createProductWithImages(ProductRequestDTO productDto, List<MultipartFile> files) throws IOException {

        if (productRepository.existsByName(productDto.getName())) {
            throw new RuntimeException("Product with this name already exists");
        }
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));
        List<Discount> discounts = productDto.getDiscountIds() != null
                ? discountRepository.findAllById(productDto.getDiscountIds())
                : List.of();
        Product product = Product.builder()
                .name(productDto.getName())
                .code(GenerateCodeProduct.generateCodeProduct()) // auto gen code
                .slug(GenerateSlug.generateSlug(productDto.getName())) // auto gen slug
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
        Product savedProduct = productRepository.save(product);

        List<ProductVariant> variants = variantService.createProductVariant(
                savedProduct,
                productDto.getVariants()
        );
        List<ProductImage> productImages = imageService.uploadImagesByColor(
                files,
                productDto.getColorImageMapping(),
                savedProduct.getId()
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
    public TypeResponse<ProductDTO> getProductById(UUID id) {
        try {
            Optional<Product> productOpt = productRepository.findById(id);
            if(productOpt.isEmpty()) {
                return ResponseHelper.notFound("Không tìm thấy sản phẩm với id này");
            }
            Product product = productOpt.get();
            product.incrementView();
            return ResponseHelper.ok(mapToProductDto(product), "Lấy sản phẩm thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi lấy sản phẩm theo id");
        }
    }

    @Override
    public TypeResponse<Page<ProductDTO>> getProductByTag(String genderSlug , String tag, Pageable pageable) {
        try {
            String tagEnum = tag.toUpperCase();
            Specification<Product> productSpecification = Specification
                    .where(hasGenderSlug(genderSlug))
                    .and(hasTag(ProductTagEnum.valueOf(tagEnum)));
            Page<Product> productPage = productRepository.findAll(productSpecification, pageable);
            if(productPage.isEmpty()) {
                return ResponseHelper.notFound(String.format("Không tìm thấy sản phẩm với %s và %s ", tag, genderSlug));
            }
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), "Lấy danh sách sản phẩm thành công");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi khi lấy sản phẩm theo tag");
        }
    }

    @Override
    public TypeResponse<ProductDTO> getProductBySlug(String slug) {
        Product product = productRepository.findProductsBySlug(slug);
        if(product == null) {
            return ResponseHelper.notFound("Không tìm thấy sản phẩm với này");
        }
        return ResponseHelper.ok(mapToProductDto(product), "Lấy sản phẩm thành công");
    }

    @Override
    public TypeResponse<Page<ProductDTO>> filterProduct(String categorySlug, String genderSlug, String colorCode,
                                          String sizeValue, Double minPrice, Double maxPrice, PageRequest pageRequest) {
        // Nếu có cate thì kiểm tra xem có parent không nếu có thì search parent chính nó còn nếu kh search chính nó
        Specification<Product> spec = Specification.where(ProductSpecification.hasStatus(true));

        // Xử lý bộ lọc theo Category
        if (categorySlug != null) {
            Optional<Category> categoryOpt = categoryRepository.findBySlug(categorySlug);
            if (categoryOpt.isPresent() && categoryOpt.get().getParent() == null) {
                // Nếu category là parent thì tìm theo điều kiện:
                // Sản phẩm thuộc category có slug = categorySlug OR sản phẩm thuộc category con của category đó
                spec = spec.and((root, query, cb) -> cb.or(
                        cb.equal(root.get("category").get("slug"), categorySlug),
                        cb.equal(root.get("category").get("parent").get("slug"), categorySlug)
                ));
            } else {
                // Nếu không phải parent thì lọc theo categorySlug của chính category đó
                spec = spec.and(ProductSpecification.hasCategorySlug(categorySlug));
            }
        }
        if (genderSlug != null) {
            spec = spec.and(ProductSpecification.hasGenderSlug(genderSlug));
        }
        if (colorCode != null) {
            spec = spec.and(ProductSpecification.hasColorCode(colorCode));
        }
        if (sizeValue != null) {
            spec = spec.and(ProductSpecification.hasSizeValue(sizeValue));
        }
        if (minPrice != null && maxPrice != null) {
            spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
        }
        // Tìm kiếm sản phẩm theo điều kiện và phân trang
        Page<Product> productPage = productRepository.findAll(spec, pageRequest);
        if(productPage.isEmpty()) {
            return ResponseHelper.notFound("Không tìm thấy sản phẩm với điều kiện lọc");
        }
        // Chuyển đổi Page<Product> sang Page<ProductDTO>
        return ResponseHelper.ok(productPage.map(this::mapToProductDto), "Lấy danh sách sản phẩm thành công");
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(UUID productId, ProductRequestDTO productDto, List<MultipartFile> files) throws IOException {

        // Fetch the existing product from the database
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));

        // Update basic fields of the product
        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setOriginPrice(productDto.getOriginPrice());
        existingProduct.setTag(productDto.getTag());
        existingProduct.setSummary(productDto.getSummary());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setStatus(productDto.getStatus());
        existingProduct.setIsFreeShip(productDto.getIsFreeShip());

        // Update category
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));
        existingProduct.setCategory(category);

        // Update discounts
        List<Discount> discounts = productDto.getDiscountIds() != null
                ? discountRepository.findAllById(productDto.getDiscountIds())
                : List.of();
        existingProduct.setDiscounts(discounts);

        // Update product variants
        List<ProductVariant> variants = variantService.createProductVariant(existingProduct, productDto.getVariants());
        existingProduct.setProductVariants(variants);

        // Delete old images before uploading new ones
        deleteOldImages(existingProduct);

        // Update product images by color
        if (files != null && !files.isEmpty() && productDto.getColorImageMapping() != null) {
            List<ProductImage> productImages = imageService.uploadImagesByColor(
                    files,
                    productDto.getColorImageMapping(),
                    productId
            );

            // Set the first image as the featured image
            Optional<ProductImage> thumbnailImage = productImages.stream()
                    .filter(image -> Boolean.TRUE.equals(image.getIsThumbnail()))
                    .findFirst();
            thumbnailImage.ifPresent(image -> existingProduct.setFeaturedImage(image.getUrl()));

            existingProduct.setImages(productImages);
        }

        // Save the updated product
        productRepository.save(existingProduct);

        // Return updated product DTO
        return mapToProductDto(existingProduct);
    }

    private void deleteOldImages(Product product) {
        // Delete all old images associated with the product
        List<ProductImage> oldImages = product.getImages();
        if (oldImages != null && !oldImages.isEmpty()) {
            // Delete images from Cloudinary or file system if needed
            for (ProductImage image : oldImages) {
                // Assuming you have a method to delete image from Cloudinary or filesystem
                cloudinaryService.deleteFile(image.getUrl()); // Or file system delete logic
            }

            // Remove old images from the product entity
            product.setImages(List.of()); // Clear the existing images
        }
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
    public ProductDTO mapToProductDto(Product product) {
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
                .categorySlug(product.getCategory().getSlug())
                .genderId(product.getCategory().getGender().getId())
                .genderName(product.getCategory().getGender().getName())
                .genderSlug(product.getCategory().getGender().getSlug())
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
                .colorCode(productVariant.getColor().getCode())
                .colorValue(productVariant.getColor().getValue())
                .colorName(productVariant.getColor().getName())
                .colorId(productVariant.getColor().getId())
                .sizeId(productVariant.getSize().getId())
                .sizeName(productVariant.getSize().getValue())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }
}
