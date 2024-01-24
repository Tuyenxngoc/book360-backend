package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.request.CreateProductRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import com.tuyenngoc.bookstore.domain.mapper.ProductMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.AuthorRepository;
import com.tuyenngoc.bookstore.repository.CategoryRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.service.ProductRedisService;
import com.tuyenngoc.bookstore.service.ProductService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;

    private final ProductRedisService productRedisService;

    private final MessageSource messageSource;

    private final ProductMapper productMapper;

    @Override
    public PaginationResponseDto<GetProductsResponseDto> findProducts(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<GetProductsResponseDto> page = productRepository.getProducts(requestDto.getKeyword(), pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        return Optional.ofNullable(productRedisService.getProducts(-1, pageable))
                .orElseGet(() -> {
                    Page<GetProductsResponseDto> page = productRepository.getProducts(pageable);
                    PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

                    PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
                    responseDto.setItems(page.getContent());
                    responseDto.setMeta(pagingMeta);

                    productRedisService.saveProducts(-1, responseDto, pageable);

                    return responseDto;
                });
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId, PaginationFullRequestDto requestDto) {

        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        return Optional.ofNullable(productRedisService.getProducts(categoryId, pageable))
                .orElseGet(() -> {
                    Page<GetProductsResponseDto> page = productRepository.getProductsByCategoryId(categoryId, pageable);
                    PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

                    PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
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
    public List<GetProductsResponseDto> getProductsSameAuthor(int productId, PaginationRequestDto request) {

        Pageable pageable = PaginationUtil.buildPageable(request);

        return Optional.ofNullable(productRedisService.getProductsSameAuthor(productId, pageable))
                .orElseGet(() -> {
                    Page<GetProductsResponseDto> page = productRepository.getProductsSameAuthor(productId, pageable);

                    productRedisService.saveProductsSameAuthor(productId, page.getContent(), pageable);

                    return page.getContent();
                });
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProductsByAuthorId(int authorId, PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<GetProductsResponseDto> page = productRepository.getProductsByAuthorId(authorId, pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public int getStockQuantityProducts() {
        return productRepository.getStockQuantityProducts();
    }

    @Override
    public PaginationResponseDto<Product> getProductsForAdmin(
            PaginationFullRequestDto requestDto,
            int sellerStockMax,
            int sellerStockMin,
            int soldMax,
            int soldMin,
            int categoryId
    ) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<Product> page = productRepository.getProductsForAdmin(requestDto.getKeyword(), pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<Product> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public ProductImage createProductImage(String image, Product product) {
        ProductImage productImage = new ProductImage();
        productImage.setImage(image);
        productImage.setProduct(product);
        return productImage;
    }

    @Override
    public Product createProduct(CreateProductRequestDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(productDto.getCategoryId())));

        Set<Author> authors = new HashSet<>();
        for (Integer authorId : productDto.getAuthorIds()) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Author.ERR_NOT_FOUND_ID, String.valueOf(authorId)));
            authors.add(author);
        }

        Product product = productMapper.toProduct(productDto);

        List<ProductImage> images = new ArrayList<>();
        for (String image : productDto.getImageURLs()) {
            images.add(createProductImage(image, product));
        }

        product.setImages(images);
        product.setFeaturedImage(images.get(0).getImage());
        product.setAuthors(authors);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product getProduct(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
    }

    @Override
    public CommonResponseDto deleteProduct(int productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(message);
        } else {
            throw new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, String.valueOf(productId));
        }
    }
}
