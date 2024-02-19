package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto(p) " +
            "FROM Product p WHERE " +
            "LOWER(p.name) LIKE %:keyword% AND " +
            "p.deleteFlag = false")
    Page<GetProductResponseDto> findProducts(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto(p) " +
            "FROM Product p WHERE " +
            "p.deleteFlag = false")
    Page<GetProductResponseDto> getProducts(Pageable pageable);

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto(p) " +
            "FROM Product p WHERE" +
            " p.category.id= :categoryId AND " +
            "p.deleteFlag = false")
    Page<GetProductResponseDto> getProductsByCategoryId(
            @Param("categoryId") int categoryId,
            Pageable pageable
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto(p) " +
            "FROM Product p JOIN p.authors a WHERE " +
            "p.deleteFlag = false AND " +
            "a IN (SELECT a2 FROM Product p2 JOIN p2.authors a2 WHERE p2.id = :productId) AND " +
            "p.id <> :productId")
    Page<GetProductResponseDto> getProductsSameAuthor(
            @Param("productId") int productId,
            Pageable pageable
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto(p) " +
            "FROM Product p JOIN p.authors a WHERE " +
            "a.id = :authorId AND " +
            "p.deleteFlag = false")
    Page<GetProductResponseDto> getProductsByAuthorId(
            @Param("authorId") int authorId,
            Pageable pageable
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductDetailResponseDto(p) " +
            "FROM Product p WHERE " +
            "p.id= :productId AND " +
            "p.deleteFlag = false")
    Optional<GetProductDetailResponseDto> getProductDetail(@Param("productId") int productId);

    @Query("SELECT COUNT(p) " +
            "FROM Product p WHERE " +
            "p.stockQuantity = 0 AND " +
            "p.deleteFlag = false")
    int getCountProductSoldOut();

    @Query("SELECT SUM(p.stockQuantity) " +
            "FROM Product p WHERE " +
            "p.deleteFlag = false")
    int getStockQuantityProducts();

    @Query("SELECT p " +
            "FROM Product p WHERE " +
            "p.id= :productId AND " +
            "p.deleteFlag = false")
    Optional<Product> findById(@Param("productId") int productId);

    @Modifying
    @Transactional
    @Query("UPDATE Product p " +
            "SET p.deleteFlag = true WHERE " +
            "p.id = :productId")
    void setProductAsDeleted(@Param("productId") int productId);

}