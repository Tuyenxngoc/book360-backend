package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
