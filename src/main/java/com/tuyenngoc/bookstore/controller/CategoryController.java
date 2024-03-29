package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "API get categories")
    @GetMapping(UrlConstant.Category.GET_CATEGORIES)
    public ResponseEntity<?> getCategories(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(categoryService.getCategories(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get category")
    @GetMapping(UrlConstant.Category.GET_CATEGORY)
    public ResponseEntity<?> getCategory(@PathVariable int categoryId) {
        return VsResponseUtil.success(categoryService.getCategory(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get all categories")
    @GetMapping(UrlConstant.Category.GET_ALL_CATEGORIES)
    public ResponseEntity<?> getAllCategories() {
        return VsResponseUtil.success(categoryService.getAllCategories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get categories")
    @GetMapping(UrlConstant.Category.GET_CATEGORIES_FOR_ADMIN)
    public ResponseEntity<?> getCategoriesForAdmin(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(categoryService.getCategoriesForAdmin(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API create and update category")
    @PutMapping(UrlConstant.Category.CREATE_CATEGORY)
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(categoryService.createCategory(categoryDto, userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API delete category")
    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        return VsResponseUtil.success(categoryService.deleteCategory(categoryId));
    }
}
