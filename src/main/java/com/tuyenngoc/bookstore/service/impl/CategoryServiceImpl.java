package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.mapper.CategoryMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CategoryRepository;
import com.tuyenngoc.bookstore.service.CategoryService;
import com.tuyenngoc.bookstore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final MessageSource messageSource;

    private final UploadFileUtil uploadFileUtil;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category;
        if (categoryDto.getId() == -1) {
            category = categoryMapper.toCategory(categoryDto);
        } else {
            category = categoryRepository.findById(categoryDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(categoryDto.getId())));

            category.setName(categoryDto.getName());
            category.setImage(categoryDto.getImage());
        }
        Category newCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        Category newCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(categoryId)));
        return categoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public CommonResponseDto updateCategory(int categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(categoryId)));

        uploadFileUtil.destroyFileWithUrl(categoryDto.getImage());

        category.setName(categoryDto.getName());
        category.setImage(categoryDto.getImage());

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto deleteCategory(int categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return new CommonResponseDto(SuccessMessage.DELETE);
        } else {
            throw new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, String.valueOf(categoryId));
        }
    }

}
