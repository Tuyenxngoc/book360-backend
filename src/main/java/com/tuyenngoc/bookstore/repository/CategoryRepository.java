package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetCategoriesResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.CategoryDto(c) FROM Category c")
    Page<CategoryDto> getCategories(Pageable pageable);

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetCategoriesResponseDto(c) FROM Category c")
    Page<GetCategoriesResponseDto> getAllCategories(Pageable pageable);
}
