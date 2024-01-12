package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getCategory(int categoryId);

    List<CategoryDto> getCategories();

    CategoryDto createCategory(CategoryDto categoryDto);

    CommonResponseDto updateCategory(int categoryId, CategoryDto categoryDto);

    CommonResponseDto deleteCategory(int categoryId);

}
