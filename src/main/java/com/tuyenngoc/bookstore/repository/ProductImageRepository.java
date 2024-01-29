package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImage pi WHERE pi.product.id =:productId")
    void deleteAllByProductId(@Param("productId") int productId);

}