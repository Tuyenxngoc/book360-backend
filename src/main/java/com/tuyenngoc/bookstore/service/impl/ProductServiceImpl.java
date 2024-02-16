package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.filter.FilterProduct;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.request.CreateProductRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import com.tuyenngoc.bookstore.domain.mapper.ProductMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.ProductImageRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.service.*;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import com.tuyenngoc.bookstore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tuyenngoc.bookstore.specifications.ProductSpecifications.filterProducts;
import static com.tuyenngoc.bookstore.specifications.ProductSpecifications.isNotDeleted;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final AuthorService authorService;

    private final ProductRedisService productRedisService;

    private final CategoryService categoryService;

    private final BookSetService bookSetService;

    private final MessageSource messageSource;

    private final ProductMapper productMapper;

    private final UploadFileUtil uploadFileUtil;

    private final UploadRedisService uploadRedisService;

    @Override
    public PaginationResponseDto<GetProductResponseDto> findProducts(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<GetProductResponseDto> page = productRepository.findProducts(requestDto.getKeyword(), pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public PaginationResponseDto<GetProductResponseDto> getProducts(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        return Optional.ofNullable(productRedisService.getProducts(-1, pageable))
                .orElseGet(() -> {
                    Page<GetProductResponseDto> page = productRepository.getProducts(pageable);
                    PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

                    PaginationResponseDto<GetProductResponseDto> responseDto = new PaginationResponseDto<>();
                    responseDto.setItems(page.getContent());
                    responseDto.setMeta(pagingMeta);

                    productRedisService.saveProducts(-1, responseDto, pageable);

                    return responseDto;
                });
    }

    @Override
    public PaginationResponseDto<GetProductResponseDto> getProductsByCategoryId(int categoryId, PaginationFullRequestDto requestDto) {

        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        return Optional.ofNullable(productRedisService.getProducts(categoryId, pageable))
                .orElseGet(() -> {
                    Page<GetProductResponseDto> page = productRepository.getProductsByCategoryId(categoryId, pageable);
                    PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

                    PaginationResponseDto<GetProductResponseDto> responseDto = new PaginationResponseDto<>();
                    responseDto.setItems(page.getContent());
                    responseDto.setMeta(pagingMeta);

                    productRedisService.saveProducts(categoryId, responseDto, pageable);

                    return responseDto;
                });
    }

    @Override
    public GetProductDetailResponseDto getProductDetail(int productId) {
        return Optional.ofNullable(productRedisService.getProductDetails(productId))
                .orElseGet(() -> {
                    GetProductDetailResponseDto productDetails = productRepository.getProductDetail(productId)
                            .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));

                    productRedisService.saveProductDetails(productId, productDetails);

                    return productDetails;
                });
    }

    @Override
    public List<GetProductResponseDto> getProductsSameAuthor(int productId, PaginationRequestDto request) {

        Pageable pageable = PaginationUtil.buildPageable(request);

        return Optional.ofNullable(productRedisService.getProductsSameAuthor(productId, pageable))
                .orElseGet(() -> {
                    Page<GetProductResponseDto> page = productRepository.getProductsSameAuthor(productId, pageable);

                    productRedisService.saveProductsSameAuthor(productId, page.getContent(), pageable);

                    return page.getContent();
                });
    }

    @Override
    public PaginationResponseDto<GetProductResponseDto> getProductsByAuthorId(int authorId, PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<GetProductResponseDto> page = productRepository.getProductsByAuthorId(authorId, pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public int getStockQuantityProducts() {
        return productRepository.getStockQuantityProducts();
    }

    @Override
    public PaginationResponseDto<Product> getProductsForAdmin(PaginationFullRequestDto requestDto, FilterProduct filter) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<Product> page = productRepository.findAll(
                filterProducts(requestDto.getKeyword(),
                        requestDto.getSearchBy(),
                        filter.getSellerStockMax(),
                        filter.getSellerStockMin(),
                        filter.getSoldMax(),
                        filter.getSoldMin(),
                        filter.getCategoryId())
                        .and(isNotDeleted()),
                pageable
        );

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<Product> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public ProductImage createProductImage(String image, Product product) {
        ProductImage productImage = new ProductImage();
        productImage.setUrl(image);
        productImage.setProduct(product);
        return productImage;
    }

    @Override
    public Product createProduct(CreateProductRequestDto productDto, String username) {
        Product product;
        if (productDto.getId() == null) {//create product
            product = productMapper.toProduct(productDto);
        } else {//update product
            product = getProduct(productDto.getId());
            // remove from cloudinary
            for (ProductImage productImage : product.getImages()) {
                if (!productDto.getImageURLs().contains(productImage.getUrl())) {
                    uploadFileUtil.destroyFileWithUrl(productImage.getUrl());
                }
            }
            // remove product images
            productImageRepository.deleteAllByProductId(productDto.getId());
            //Set new values
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setStockQuantity(productDto.getStockQuantity());
            product.setPrice(productDto.getPrice());
            product.setDiscount(productDto.getDiscount());
            if (productDto.getIsbn() != null) {
                product.setIsbn(productDto.getIsbn());
            }
            if (productDto.getPublisher() != null) {
                product.setPublisher(productDto.getPublisher());
            }
            if (productDto.getSize() != null) {
                product.setSize(productDto.getSize());
            }
            if (productDto.getCoverType() != null) {
                product.setCoverType(productDto.getCoverType());
            }
            if (productDto.getAgeClassifications() != null) {
                product.setAgeClassifications(productDto.getAgeClassifications());
            }
            if (productDto.getWeight() != null) {
                product.setWeight(productDto.getWeight());
            }
            if (productDto.getPageCount() != null) {
                product.setPageCount(productDto.getPageCount());
            }
        }

        if (productDto.getBookSetId() != null) {
            product.setBookSet(bookSetService.getBookSet(productDto.getBookSetId()));
        } else {
            product.setBookSet(null);
        }

        Category category = categoryService.getCategory(productDto.getCategoryId());

        Set<Author> authors = new HashSet<>();
        for (Integer authorId : productDto.getAuthorIds()) {
            authors.add(authorService.getAuthor(authorId));
        }

        List<ProductImage> images = new ArrayList<>();
        for (String image : productDto.getImageURLs()) {
            images.add(createProductImage(image, product));
        }

        product.setImages(images);
        product.setFeaturedImage(images.get(0).getUrl());
        product.setAuthors(authors);
        product.setCategory(category);

        //Delete image urls from redis cache
        uploadRedisService.deleteUrls(username, productDto.getImageURLs());

        return productRepository.save(product);
    }

    @Override
    public Product getProduct(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
    }

    @Override
    public CommonResponseDto deleteProduct(int productId) {
        productRepository.setProductAsDeleted(productId);

        // Delete cache
        productRedisService.clearAllProductCache();
        productRedisService.clearProductDetailCache(productId);
        productRedisService.clearProductSameAuthorCache(productId);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }
}
