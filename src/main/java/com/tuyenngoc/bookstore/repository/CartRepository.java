package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.ProductFromCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByCustomerId(int customerId);

    @Query("SELECT COALESCE(SUM(cd.quantity), 0) FROM CartDetail cd WHERE cd.cart.customer.id = :customerId")
    Integer getTotalProductQuantityByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.ProductFromCartResponseDto(cd) FROM CartDetail cd WHERE cd.cart.customer.id = :customerId")
    List<ProductFromCartResponseDto> getProductFromCart(@Param("customerId") Integer customerId);
}