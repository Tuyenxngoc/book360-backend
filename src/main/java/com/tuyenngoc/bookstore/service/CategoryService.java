package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetCategoriesResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    //User
    PaginationResponseDto<CategoryDto> getCategories(PaginationFullRequestDto requestDto);

    //Admin
    Category getCategory(int categoryId);

    List<Category> getAllCategories();

    PaginationResponseDto<GetCategoriesResponseDto> getCategoriesForAdmin(PaginationFullRequestDto requestDto);

    Category createCategory(CategoryDto categoryDto);

    CommonResponseDto deleteCategory(int categoryId);

}
