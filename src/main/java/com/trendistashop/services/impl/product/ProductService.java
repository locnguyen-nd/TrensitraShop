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
import com.trendistashop.repositories.product.*;
import com.trendistashop.services.CloudinaryService;
import com.trendistashop.services.IProductService;
import com.trendistashop.specifications.ProductSpecification;
import com.trendistashop.utils.ResponseHelper;
import com.trendistashop.dto.response.*;
import com.trendistashop.entities.product.*;
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
    private final SizeRepository sizeRepository;
    private final DiscountService discountService;
    private final VariantService variantService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final ProductVariantRepository productVariantRepository;
    private final int suggestionLimit;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          DiscountRepository discountRepository, ColorRepository colorRepository,
                          SizeRepository sizeRepository, DiscountService discountService,
                          VariantService variantService, CloudinaryService cloudinaryService, ModelMapper modelMapper, ImageRepository imageRepository,
                          ProductVariantRepository productVariantRepository, @Value("${search.suggestion.limit}") int suggestionLimit) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.discountRepository = discountRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.discountService = discountService;
        this.variantService = variantService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
        this.productVariantRepository = productVariantRepository;
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
    public TypeResponse<ProductDTO> createProduct(@Valid ProductRequestDTO productDto) {
        try {
            if (productRepository.existsByName(productDto.getName())) {
                throw new RuntimeException("Product with this name already exists");
            }
            // Tạo sản phẩm cơ bản
            Product product = buildBasicProduct(productDto);

            productRepository.save(product);
            // Cập nhật các phần liên quan
            updateCategory(product, productDto.getCategoryId());
            updateDiscounts(product, productDto.getDiscountIds());
            updateVariants(product, productDto.getVariants());
            handleImages(product, productDto.getVariants());
            productRepository.save(product);
            return ResponseHelper.ok(mapToProductDto(product), ResponseMessage.CREATE_SUCCESS);
        } catch (ResourceNotFoundEx e) {
            log.warn("Không tìm thấy tài nguyên: {}", e.getMessage());
            return ResponseHelper.notFound(e.getMessage());

        } catch (IllegalArgumentException e) {
            log.warn("Dữ liệu không hợp lệ: {}", e.getMessage());
            return ResponseHelper.badRequest("Dữ liệu không hợp lệ: " + e.getMessage());

        } catch (RuntimeException e) {
            log.warn("Lỗi nghiệp vụ: {}", e.getMessage());
            return ResponseHelper.badRequest(e.getMessage());

        } catch (Exception e) {
            log.error("Lỗi hệ thống khi tạo sản phẩm", e);
            return ResponseHelper.serverError("Tạo sản phẩm thất bại. Vui lòng thử lại!"); // KHÔNG THROW e
        }
    }

    @Override
    @Transactional
    public TypeResponse<ProductDTO> updateProduct(UUID productId, @Valid ProductRequestDTO productDto) {
        try {
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
            return ResponseHelper.ok(mapToProductDto(product), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("Error updating product", e);
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
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
    public TypeResponse<Page<ProductDTO>> filterProduct(String keyword, String tag, String categorySlug, String genderSlug, String colorCode,
                                                        String sizeValue, Double minPrice, Double maxPrice, Boolean status, PageRequest pageRequest) {
        try {
            Specification<Product> spec = Specification.where(null);
            if (status != null) {
                spec = Specification.where(ProductSpecification.hasStatus(status));
            }
            if (categorySlug != null) {
                log.info("Filtering by categorySlug: {}", categorySlug);
                Category categoryOpt = categoryRepository.findBySlug(categorySlug);
                if (categoryOpt == null) {
                    log.info("Category not found for slug: {}", categorySlug);
                    return ResponseHelper.notFound(String.format(ResponseMessage.CATEGORY_NOT_FOUND, categorySlug));
                }
                log.info("Category found: slug = {}, parent = {}",
                        categoryOpt.getSlug(),
                        categoryOpt.getParent() != null ? categoryOpt.getParent().getSlug() : "null");

                spec = spec.and((root, query, cb) -> cb.or(
                        cb.equal(root.get("category").get("slug"), categorySlug),
                        cb.equal(root.get("category").get("parent").get("slug"), categorySlug)
                ));
            }
            if (tag != null) {
                spec = spec.and(ProductSpecification.hasTag(ProductTagEnum.valueOf(tag.toUpperCase())));
            }
            if (keyword != null) {
                spec = spec.and(ProductSpecification.hasNameOrSlug(keyword));
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
            log.info("Get product successfully: {} product", productPage.getTotalElements());
            return ResponseHelper.ok(productPage.map(this::mapToProductDto), ResponseMessage.FETCH_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    @Transactional
    public TypeResponse<Void> deleteProduct(UUID id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
            imageRepository.deleteAll(product.getImages());
            productVariantRepository.deleteAll(product.getProductVariants());
            productRepository.delete(product);
            log.info("Delete product successfully");
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

    @Override
    public TypeResponse<Void> updateProductStatus(UUID id, boolean status) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
            product.setStatus(status);
            productRepository.save(product);
            log.info("Update product successfully");
            return ResponseHelper.ok(null, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    public TypeResponse<Void> updateProductQuantities(UUID id, int availableQuantities) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundEx("Product not found"));
            int totalQuantity = product.getProductVariants()
                    .stream()
                    .filter(Objects::nonNull)
                    .mapToInt(ProductVariant::getStockQuantity)
                    .sum();
            if (availableQuantities < 0 || availableQuantities > totalQuantity) {
                return ResponseHelper.badRequest("Available quantities must be between 0 and " + totalQuantity);
            }
            return ResponseHelper.ok(null, ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    public ProductVariant productVariantById(UUID id) {
        return variantService.getVariantById(id);
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
        product.setOriginPrice(dto.getOriginPrice());
        product.setPrice(dto.getPrice());
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
        if (product.getDiscounts() == null) {
            product.setDiscounts(new ArrayList<>());
        }
        product.getDiscounts().clear();

        if (discountIds != null && !discountIds.isEmpty()) {
            List<Discount> discounts = discountRepository.findAllById(discountIds);
            product.getDiscounts().addAll(discounts);
            try {
                discountService.calculateFinalPriceAndUpdateProduct(product.getId());
            } catch (Exception e) {
                log.warn("Không thể tính giá sau giảm (có thể discount không hợp lệ): {}", e.getMessage());
            }
        }
    }
    private void updateVariants(Product managedProduct, List<VariantRequestDTO> variants) {
        List<ProductVariant> existingVariants = managedProduct.getProductVariants() != null ? managedProduct.getProductVariants() : new ArrayList<>();
        if (existingVariants.isEmpty()) {
            // Trường hợp tạo mới
            List<ProductVariant> newVariants = variantService.createProductVariant(managedProduct, variants);
            managedProduct.setProductVariants(newVariants);
            log.info("Tạo mới {} variants", newVariants.size());
        }
        else {
            // Trường hợp cập nhật
            List<ProductVariant> updatedVariants = variantService.updateVariant(managedProduct, variants);
            managedProduct.getProductVariants().clear();
            managedProduct.getProductVariants().addAll(updatedVariants);
            log.info("Cập nhật {} variants (thêm/bớt/cập nhật)", updatedVariants.size());
        }
    }

    private void handleImages(Product product, List<VariantRequestDTO> variants) {
        // Lấy danh sách hình ảnh cũ
        List<ProductImage> oldImages = product.getImages() != null ? new ArrayList<>(product.getImages()) : new ArrayList<>();
        List<ProductImage> newProductImages = new ArrayList<>();
        Set<String> newImageUrls = new HashSet<>();
        // Lấy danh sách variant hiện tại của sản phẩm sau khi cập nhật
        List<ProductVariant> currentVariants = product.getProductVariants() != null ? product.getProductVariants() : new ArrayList<>();
        Set<String> validVariantKeys = currentVariants.stream()
                .map(v -> v.getColor().getId() + "_" + v.getSize().getId())
                .collect(Collectors.toSet());
        // Lưu trữ danh sách ảnh theo variant
        Map<String, Set<String>> variantImageUrls = new HashMap<>();

        // Tạo danh sách hình ảnh mới từ variants
        if (variants != null) {
            for (VariantRequestDTO variant : variants) {
                if (variant.getImages() != null && !variant.getImages().isEmpty()) {
                    Color color = colorRepository.findById(variant.getColorId())
                            .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy màu sắc"));
                    Size size = sizeRepository.findById(variant.getSizeId())
                            .orElseThrow(() -> new ResourceNotFoundEx("Không tìm thấy size"));

                    // Kiểm tra variant có tồn tại trong danh sách variant hiện tại không
                    String variantKey = color.getId() + "_" + size.getId();
                    if (!validVariantKeys.contains(variantKey)) {
                        log.warn("Variant với colorId {} và sizeId {} không tồn tại trong sản phẩm",
                                variant.getColorId(), variant.getSizeId());
                        continue;
                    }

                    // Lưu trữ URL ảnh theo variant
                    Set<String> imageUrlsForVariant = new HashSet<>();
                    for (ImageRequestDTO imageDto : variant.getImages()) {
                        if (imageDto.getUrl() != null && !imageDto.getUrl().isEmpty()) {
                            // Kiểm tra xem URL đã tồn tại trong oldImages chưa
                            Optional<ProductImage> existingImage = oldImages.stream()
                                    .filter(img -> img.getUrl().equals(imageDto.getUrl())
                                            && img.getColor().getId().equals(color.getId())
                                            && img.getSize().getId().equals(size.getId()))
                                    .findFirst();

                            if (existingImage.isPresent()) {
                                // Cập nhật thông tin cho hình ảnh cũ
                                ProductImage image = existingImage.get();
                                image.setIsThumbnail(imageDto.getIsThumbnail() != null ? imageDto.getIsThumbnail() : false);
                                image.setOrder(imageDto.getOrder());
                                newProductImages.add(image);
                            } else {
                                // Tạo mới hình ảnh
                                ProductImage image = ProductImage.builder()
                                        .url(imageDto.getUrl())
                                        .isThumbnail(imageDto.getIsThumbnail() != null ? imageDto.getIsThumbnail() : false)
                                        .product(product)
                                        .color(color)
                                        .size(size)
                                        .order(imageDto.getOrder())
                                        .build();
                                newProductImages.add(image);
                            }
                            imageUrlsForVariant.add(imageDto.getUrl());
                            newImageUrls.add(imageDto.getUrl());
                        }
                    }
                    variantImageUrls.put(variantKey, imageUrlsForVariant);
                }
            }
        }

        // Xóa hình ảnh cũ không còn trong danh sách mới hoặc thuộc variant không hợp lệ
        oldImages.forEach(oldImage -> {
            String variantKey = oldImage.getColor().getId() + "_" + oldImage.getSize().getId();
            Set<String> imageUrlsForVariant = variantImageUrls.getOrDefault(variantKey, Collections.emptySet());

            // Xóa bản ghi trong DB nếu ảnh không còn trong danh sách của variant hoặc variant không hợp lệ
            if (!imageUrlsForVariant.contains(oldImage.getUrl()) || !validVariantKeys.contains(variantKey)) {
                try {
                    // Chỉ xóa trên Cloudinary nếu URL không còn được sử dụng bởi bất kỳ variant nào
                    boolean isImageUsedElsewhere = newProductImages.stream()
                            .anyMatch(img -> img.getUrl().equals(oldImage.getUrl()));
                    if (!isImageUsedElsewhere) {
                        cloudinaryService.deleteFile(oldImage.getUrl());
                        log.info("Deleted image {} from Cloudinary as it is not used by any variant", oldImage.getUrl());
                    } else {
                        log.info("Kept image {} on Cloudinary as it is used by another variant", oldImage.getUrl());
                    }
                    imageRepository.delete(oldImage);
                    log.info("Deleted image record {} for variant {} from database", oldImage.getUrl(), variantKey);
                } catch (Exception e) {
                    log.error("Failed to delete image: " + oldImage.getUrl(), e);
                }
            }
        });

        // Thiết lập hình ảnh nổi bật
        Optional<ProductImage> thumbnailImage = newProductImages.stream()
                .filter(ProductImage::getIsThumbnail)
                .findFirst();
        thumbnailImage.ifPresent(image -> product.setFeaturedImage(image.getUrl()));

        // Cập nhật danh sách hình ảnh
        product.setImages(newProductImages);
    }

    private BigDecimal getFinalPriceAfterDiscount(UUID productId) {
        BigDecimal discountValue = discountService.calculateFinalPriceAndUpdateProduct(productId);
        return discountValue;
    }

    public ProductDTO mapToProductDto(Product product) {
        BigDecimal finalProductPrice = discountService.calculateFinalPriceAndUpdateProduct(product.getId());
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .thumbnail(product.getFeaturedImage())
                .code(product.getCode())
                .slug(product.getSlug())
                .summary(product.getSummary())
                .description(product.getDescription())
                .status(product.getStatus())
//                .discountValue(getFinalPriceAfterDiscount(product.getId()))
                .originPrice(product.getOriginPrice())
                .price(finalProductPrice)
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
                .productVariants(product.getProductVariants()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(productVariant -> productVariant.getColor() != null)
                        .filter(productVariant -> productVariant.getSize() != null)
                        .map(variant -> convertVariantDTO(variant, product.getImages()))
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
                .order(productImage.getOrder())
                .isThumbnail(productImage.getIsThumbnail())
                .build();
    }

    private VariantDTO convertVariantDTO(ProductVariant productVariant, List<ProductImage> productImages) {
        List<ProductImageDTO> imageDTOs = productImages.stream()
                .filter(image -> image.getColor() != null && image.getColor().getId().equals(productVariant.getColor().getId()))
                .filter(image -> image.getSize() != null && image.getSize().getId().equals(productVariant.getSize().getId()))
                .sorted(Comparator.comparing(ProductImage::getOrder))
                .map(this::covertImageToDTO)
                .collect(Collectors.toList());
        return VariantDTO.builder()
                .id(productVariant.getId())
                .order(productVariant.getOrder())
                .codeVariant(productVariant.getCodeVariant())
                .colorCode(productVariant.getColor().getCode())
                .colorValue(productVariant.getColor().getValue())
                .colorName(productVariant.getColor().getName())
                .colorId(productVariant.getColor().getId())
                .sizeId(productVariant.getSize().getId())
                .sizeName(productVariant.getSize().getValue())
                .stockQuantity(productVariant.getStockQuantity())
                .price(productVariant.getPrice())
                .productImages(imageDTOs)
                .build();
    }
}