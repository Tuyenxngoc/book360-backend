package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT p FROM Customer c JOIN c.favoriteProducts p WHERE c.id = :customerId AND p.id = :productId")
    List<Product> findFavoriteProductsByCustomerIdAndProductId(@Param("customerId") Long customerId, @Param("productId") Long productId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Customer c JOIN c.favoriteProducts p WHERE c.id = :customerId AND p.id = :productId")
    boolean isProductFavoriteForCustomer(@Param("customerId") Integer customerId, @Param("productId") Integer productId);

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto(p) FROM Product p JOIN p.customers c WHERE c.id = :customerId")
    Page<GetProductsResponseDto> getFavoriteProducts(@Param("customerId") int customerId, Pageable pageable);
}