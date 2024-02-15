package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    PaginationResponseDto<CategoryDto> getCategories(PaginationFullRequestDto requestDto);

    Category getCategory(int categoryId);

    List<Category> getAllCategories();

    PaginationResponseDto<Category> getCategoriesForAdmin(PaginationFullRequestDto requestDto);

    Category createCategory(CategoryDto categoryDto, String username);

    CommonResponseDto deleteCategory(int categoryId);

}
