package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Customer c JOIN c.favoriteProducts p WHERE " +
            "c.id = :customerId AND " +
            "p.id = :productId")
    boolean isProductFavoriteForCustomer(
            @Param("customerId") int customerId,
            @Param("productId") int productId
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.product.GetProductResponseDto(p) " +
            "FROM Product p JOIN p.customers c WHERE " +
            "c.id = :customerId")
    Page<GetProductResponseDto> getFavoriteProducts(
            @Param("customerId") int customerId,
            Pageable pageable
    );

    @Query("SELECT c " +
            "FROM Customer c WHERE " +
            "c.fullName LIKE %:keyword%")
    Page<Customer> getCustomersByFullName(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}