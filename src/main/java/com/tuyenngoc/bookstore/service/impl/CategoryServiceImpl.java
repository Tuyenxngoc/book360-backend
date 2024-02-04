package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetCategoryResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.mapper.CategoryMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CategoryRepository;
import com.tuyenngoc.bookstore.service.CategoryService;
import com.tuyenngoc.bookstore.service.UploadRedisService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final MessageSource messageSource;

    private final UploadRedisService uploadRedisService;

    @Override
    public Category createCategory(CategoryDto categoryDto, String username) {
        Category category;
        if (categoryDto.getId() == null) {
            category = categoryMapper.toCategory(categoryDto);
        } else {
            category = getCategory(categoryDto.getId());

            category.setName(categoryDto.getName());
            category.setImage(categoryDto.getImage());
        }
        //Delete image urls from redis cache
        uploadRedisService.deleteUrls(username, List.of(categoryDto.getImage()));

        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(categoryId)));
    }

    @Override
    public PaginationResponseDto<CategoryDto> getCategories(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.CATEGORY);

        Page<CategoryDto> page = categoryRepository.getCategories(pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.CATEGORY, page);

        PaginationResponseDto<CategoryDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public PaginationResponseDto<GetCategoryResponseDto> getCategoriesForAdmin(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.CATEGORY);

        Page<GetCategoryResponseDto> page = categoryRepository.getCategoriesForAdmin(pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.CATEGORY, page);

        PaginationResponseDto<GetCategoryResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public CommonResponseDto deleteCategory(int categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(message);
        } else {
            throw new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(categoryId));
        }
    }

}
