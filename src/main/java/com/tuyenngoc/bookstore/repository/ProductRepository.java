package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto(p) FROM Product p")
    Page<GetProductsResponseDto> getProducts(Pageable pageable);
}