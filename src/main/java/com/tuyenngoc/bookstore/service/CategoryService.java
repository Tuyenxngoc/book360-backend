package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(int categoryId);

    List<Category> getCategories();

    Category createCategory(CategoryDto categoryDto);

    CommonResponseDto updateCategory(int categoryId, CategoryDto categoryDto);

    CommonResponseDto deleteCategory(int categoryId);

}
