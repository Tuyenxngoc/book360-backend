package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.mapper.CategoryMapper;
import com.tuyenngoc.bookstore.exception.DataIntegrityViolationException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CategoryRepository;
import com.tuyenngoc.bookstore.service.CategoryService;
import com.tuyenngoc.bookstore.service.UploadRedisService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import com.tuyenngoc.bookstore.util.UploadFileUtil;
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

    private final UploadFileUtil uploadFileUtil;

    @Override
    public Category createCategory(CategoryDto categoryDto, String username) {
        Category category;
        if (categoryDto.getId() == null) {
            // check if name already exists
            boolean isNameExists = categoryRepository.existsByName(categoryDto.getName());
            if (isNameExists) {
                throw new DataIntegrityViolationException(ErrorMessage.Category.ERR_DUPLICATE_NAME, categoryDto.getName());
            }
            // mapping category
            category = categoryMapper.toCategory(categoryDto);
        } else {
            // check if name exists excluding the current category
            boolean isNameExists = categoryRepository.existsByNameAndIdNot(categoryDto.getName(), categoryDto.getId());
            if (isNameExists) {
                throw new DataIntegrityViolationException(ErrorMessage.Category.ERR_DUPLICATE_NAME, categoryDto.getName());
            }
            // get category
            category = getCategory(categoryDto.getId());
            // set new values
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
    public PaginationResponseDto<Category> getCategoriesForAdmin(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.CATEGORY);

        Page<Category> page = categoryRepository.findAll(pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.CATEGORY, page);

        PaginationResponseDto<Category> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public CommonResponseDto deleteCategory(int categoryId) {
        Category category = getCategory(categoryId);

        if (category.getProducts().size() > 0) {
            throw new DataIntegrityViolationException(ErrorMessage.Category.ERR_CANNOT_DELETE, String.valueOf(categoryId));
        }

        uploadFileUtil.destroyFileWithUrl(category.getImage());

        categoryRepository.delete(category);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

}
