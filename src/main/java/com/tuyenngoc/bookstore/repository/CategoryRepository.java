package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.CategoryDto(c) FROM Category c")
    Page<CategoryDto> getCategories(Pageable pageable);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, int categoryId);

}
