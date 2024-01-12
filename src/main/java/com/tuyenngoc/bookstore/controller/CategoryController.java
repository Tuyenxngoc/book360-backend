package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Operation(summary = "API get categories")
    @GetMapping(UrlConstant.Category.GET_CATEGORIES)
    public ResponseEntity<?> getCategories() {
        return VsResponseUtil.success(categoryService.getCategories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get category")
    @GetMapping(UrlConstant.Category.GET_CATEGORY)
    public ResponseEntity<?> getCategory(@PathVariable int categoryId) {
        return VsResponseUtil.success(categoryService.getCategory(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API create category")
    @PostMapping(UrlConstant.Category.CREATE_CATEGORY)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return VsResponseUtil.success(categoryService.createCategory(categoryDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API update category")
    @PutMapping(value = UrlConstant.Category.UPDATE_CATEGORY)
    public ResponseEntity<?> updateCustomer(@PathVariable int categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        return VsResponseUtil.success(categoryService.updateCategory(categoryId, categoryDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API delete category")
    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        return VsResponseUtil.success(categoryService.deleteCategory(categoryId));
    }
}
