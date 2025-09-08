package com.trendistashop.services.impl.product;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.ImageRequestDTO;
import com.trendistashop.dto.request.ProductRequestDTO;
import com.trendistashop.dto.request.VariantRequestDTO;
import com.trendistashop.entities.category.Category;
import com.trendistashop.enums.ProductTagEnum;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.helper.GenerateCodeProduct;
import com.trendistashop.helper.GenerateSlug;
import com.trendistashop.repositories.category.CategoryRepository;
import com.trendistashop.repositories.product.ColorRepository;
import com.trendistashop.repositories.product.DiscountRepository;
import com.trendistashop.repositories.product.ProductRepository;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.services.IProductService;
import com.trendistashop.specifications.ProductSpecification;
import com.trendistashop.utils.ResponseHelper;
import com.trendistashop.dto.response.*;
import com.trendistashop.entities.product.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final ColorRepository colorRepository;
    private final DiscountService discountService;
    private final VariantService variantService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final int suggestionLimit;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          DiscountRepository discountRepository, ColorRepository colorRepository,
                          DiscountService discountService,
                          VariantService variantService, CloudinaryService cloudinaryService, ModelMapper modelMapper,
                          @Value("${search.suggestion.limit}") int suggestionLimit) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.discountRepository = discountRepository;
        this.colorRepository = colorRepository;
        this.discountService = discountService;
        this.variantService = variantService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.suggestionLimit = suggestionLimit;
    }

    @Override
    public TypeResponse<Page<ProductDTO>> getAllProduct(Pageable pageable) {
        try {
            Page<Product> productPage = productRepository.findAll(pageable);
            if (productPage.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.PRODUCT_NOT_FOUND);
            }
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<SearchSuggestionDTO> getSuggestion(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return ResponseHelper.badRequest(ResponseMessage.BAD_REQUEST);
            }
            List<Product> products = productRepository.findProductNames(
                    keyword.toLowerCase().trim(),
                    PageRequest.of(0, suggestionLimit));
            List<Object[]> categories = categoryRepository.findCategoryNameAndSlugs(keyword,
                    PageRequest.of(0, suggestionLimit));

            SearchSuggestionDTO result = new SearchSuggestionDTO();
            result.setProducts(products.stream()
                    .map(this::mapToProductDto)
                    .collect(Collectors.toList()));
            result.setCategories(
                    categories.stream()
                            .map(c -> {
                                SearchSuggestionDTO.NameSlugDTO dto = new SearchSuggestionDTO.NameSlugDTO();
                                dto.setName((String) c[0]);
                                dto.setSlug((String) c[1]);
                                return dto;
                            })
                            .collect(Collectors.toList()));
            if (result.getProducts().size() == 0 && result.getCategories().size() == 0) {
                return ResponseHelper.badRequest(ResponseMessage.PRODUCT_NOT_FOUND);
            }
            return ResponseHelper.ok(result, ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<Page<ProductDTO>> searchWithName(String keyword, Pageable pageable) {
        try {
            if (keyword == null) {
                return ResponseHelper.validationError("keyword", "Tên sản phẩm không được để trống!");
            }
            Specification<Product> specification = Specification.where(ProductSpecification.hasName(keyword));
            Page<Product> productPage = productRepository.findAll(specification, pageable);
            if (productPage.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.PRODUCT_NOT_FOUND);
            }
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    @Transactional
    public ProductDTO createProduct(@Valid ProductRequestDTO productDto) {
        if (productRepository.existsByName(productDto.getName())) {
            throw new RuntimeException("Product with this name already exists");
        }

        // Tạo sản phẩm cơ bản
        Product product = buildBasicProduct(productDto);
        product = productRepository.save(product);

        // Cập nhật các phần liên quan
        updateCategory(product, productDto.getCategoryId());
        updateDiscounts(product, productDto.getDiscountIds());
        updateVariants(product, productDto.getVariants());
        handleImages(product, productDto.getVariants());
        productRepository.save(product);
        return mapToProductDto(product);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(UUID productId, @Valid ProductRequestDTO productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));

        // Cập nhật fields cơ bản
        updateBasicFields(product, productDto);

        // Cập nhật các phần liên quan
        updateCategory(product, productDto.getCategoryId());
        updateDiscounts(product, productDto.getDiscountIds());
        updateVariants(product, productDto.getVariants());
        handleImages(product, productDto.getVariants());
        productRepository.save(product);
        return mapToProductDto(product);
    }

    @Override
    public TypeResponse<ProductDTO> getProductById(UUID id) {
        try {
            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isEmpty()) {
                return ResponseHelper.notFound(ResponseMessage.PRODUCT_NOT_FOUND);
            }
            Product product = productOpt.get();
            product.incrementView();
            return ResponseHelper.ok(mapToProductDto(product), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<Page<ProductDTO>> getProductByTag(String genderSlug, String tag, Pageable pageable) {
        try {
            String tagEnum = tag.toUpperCase();
            Specification<Product> productSpecification = Specification
                    .where(ProductSpecification.hasGenderSlug(genderSlug))
                    .and(ProductSpecification.hasTag(ProductTagEnum.valueOf(tagEnum)));
            Page<Product> productPage = productRepository.findAll(productSpecification, pageable);
            if (productPage.isEmpty()) {
                return ResponseHelper.notFound(String.format("Không tìm thấy sản phẩm với %s và %s ", tag, genderSlug));
            }
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<ProductDTO> getProductBySlug(String slug) {
        Product product = productRepository.findProductsBySlug(slug);
        if (product == null) {
            return ResponseHelper.notFound(ResponseMessage.PRODUCT_NOT_FOUND);
        }
        return ResponseHelper.ok(mapToProductDto(product), ResponseMessage.FETCH_SUCCESS);
    }

    @Override
    public TypeResponse<Page<ProductDTO>> filterProduct(String categorySlug, String genderSlug, String colorCode,
                                                        String sizeValue, Double minPrice, Double maxPrice, PageRequest pageRequest) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasStatus(true));

        if (categorySlug != null) {
            log.info("Filtering by categorySlug: {}", categorySlug);
            Category categoryOpt = categoryRepository.findBySlugWithParent(categorySlug);
            if (categoryOpt == null) {
                log.info("Category not found for slug: {}", categorySlug);
                return ResponseHelper.notFound(String.format(ResponseMessage.CATEGORY_NOT_FOUND, categorySlug));
            }
            log.info("Category found: slug = {}, parent = {}",
                    categoryOpt.getSlug(),
                    categoryOpt.getParent() != null ? categoryOpt.getParent().getSlug() : "null");

            spec = spec.and((root, query, cb) -> {
                query.distinct(true);
                Join<Product, Category> categoryJoin = root.join("category", JoinType.LEFT);
                Join<Category, Category> parentJoin = categoryJoin.join("parent", JoinType.LEFT);

                return cb.or(
                        cb.equal(categoryJoin.get("slug"), categorySlug),
                        cb.equal(parentJoin.get("slug"), categorySlug)
                );
            });
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
        Page<Product> productPage = productRepository.findAll(spec, pageRequest);
        if (productPage.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.PRODUCT_NOT_FOUND);
        }
        return ResponseHelper.ok(productPage.map(this::mapToProductDto), ResponseMessage.FETCH_SUCCESS);
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

    private Product buildBasicProduct(ProductRequestDTO dto) {
        return Product.builder()
                .name(dto.getName())
                .code(GenerateCodeProduct.generateCodeProduct())
                .slug(GenerateSlug.generateSlug(dto.getName()))
                .summary(dto.getSummary())
                .originPrice(dto.getOriginPrice())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : true)
                .isFreeShip(dto.getIsFreeShip())
                .views(0)
                .ratingAverage(0)
                .ratingTotal(0)
                .unitsSold(0)
                .tag(dto.getTag())
                .build();
    }

    private void updateBasicFields(Product product, ProductRequestDTO dto) {
        product.setName(dto.getName());
        product.setSummary(dto.getSummary());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        product.setIsFreeShip(dto.getIsFreeShip());
        product.setTag(dto.getTag());
        if (!product.getName().equals(dto.getName())) {
            product.setSlug(GenerateSlug.generateSlug(dto.getName()));
        }
    }

    private void updateCategory(Product product, UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found"));
        product.setCategory(category);
    }

    private void updateDiscounts(Product product, List<UUID> discountIds) {
        List<Discount> discounts = (discountIds != null && !discountIds.isEmpty())
                ? discountRepository.findAllById(discountIds)
                : new ArrayList<>();
        product.setDiscounts(discounts);
    }

    private void updateVariants(Product product, List<VariantRequestDTO> variants) {
        if (variants != null && !variants.isEmpty()) {
            List<ProductVariant> newVariants = variantService.createProductVariant(product, variants);
            product.setProductVariants(newVariants);
        }
    }

    private void handleImages(Product product, List<VariantRequestDTO> variants) {
        // Xóa hình ảnh cũ
        deleteOldImages(product);

        // Thêm hình ảnh mới từ variants
        if (variants != null) {
            List<ProductImage> productImages = new ArrayList<>();
            for (VariantRequestDTO variant : variants) {
                if (variant.getImages() != null && !variant.getImages().isEmpty()) {
                    Color color = colorRepository.findById(variant.getColorId())
                            .orElseThrow(() -> new ResourceNotFoundEx("Color not found"));
                    for (ImageRequestDTO imageDto : variant.getImages()) {
                        if (imageDto.getUrl() != null) {
                            ProductImage image = ProductImage.builder()
                                    .url(imageDto.getUrl())
                                    .isThumbnail(imageDto.getIsThumbnail() != null ? imageDto.getIsThumbnail() : false)
                                    .product(product)
                                    .color(color)
                                    .build();
                            productImages.add(image);
                        }
                    }
                }
            }

            // Set featured image
            Optional<ProductImage> thumbnailImage = productImages.stream()
                    .filter(ProductImage::getIsThumbnail)
                    .findFirst();
            thumbnailImage.ifPresent(image -> product.setFeaturedImage(image.getUrl()));

            product.setImages(productImages);
        }
    }

    private void deleteOldImages(Product product) {
        List<ProductImage> oldImages = product.getImages();
        if (oldImages != null && !oldImages.isEmpty()) {
            oldImages.stream()
                    .forEach(image -> {
                         cloudinaryService.deleteFile(image.getUrl());
                    });
            product.setImages(new ArrayList<>());
        }
    }


    private BigDecimal getFinalPriceAfterDiscount(UUID productId) {
        BigDecimal discountValue = discountService.calculateFinalPriceAndUpdateProduct(productId);
        return discountValue;
    }

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
//                .discountValue(getFinalPriceAfterDiscount(product.getId()))
                .originPrice(product.getOriginPrice())
                .price(product.getPrice())
                .isFreeShip(product.getIsFreeShip())
                .availableQuantities(product.getProductVariants()
                        .stream().filter(Objects::nonNull)
                        .mapToInt(ProductVariant::getStockQuantity)
                        .sum())
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
                .productImages(product.getImages()
                        .stream().filter(Objects::nonNull)
                        .map(this::covertImageToDTO)
                        .toList())
                .productVariants(product.getProductVariants()
                        .stream().filter(Objects::nonNull)
                        .map(this::convertVariantDTO)
                        .toList())
                .discounts(product.getDiscounts()
                        .stream().filter(Objects::nonNull)
                        .map(this::convertDiscountToDTO)
                        .toList())
                .build();
    }
    private DiscountDTO convertDiscountToDTO(Discount discount) {
        return modelMapper.map(discount, DiscountDTO.class);
    }

    private ProductImageDTO covertImageToDTO(ProductImage productImage) {
        return ProductImageDTO.builder()
                .id(productImage.getId())
                .url(productImage.getUrl())
                .isThumbnail(productImage.getIsThumbnail())
                .productId(productImage.getProduct().getId())
                .variantId(productImage.getColor() != null ? productImage.getColor().getId() : null)
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
                .price(productVariant.getPrice())
                .build();
    }
}