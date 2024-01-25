package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    void deleteAllByProductId(int productId);

}