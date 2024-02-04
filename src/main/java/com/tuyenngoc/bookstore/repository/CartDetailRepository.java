package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

    @Query("SELECT cd " +
            "FROM CartDetail cd WHERE " +
            "cd.product.id = :productId AND " +
            "cd.cart.customer.id = :customerId")
    Optional<CartDetail> getCartDetail(int customerId, int productId);

    @Modifying
    @Transactional
    @Query("DELETE " +
            "FROM CartDetail cd WHERE " +
            "cd.cart.customer.id=:customerId AND " +
            "cd.product.id=:productId")
    void deleteCartDetail(
            @Param("customerId") int customerId,
            @Param("productId") int productId
    );

    @Modifying
    @Transactional
    void deleteAllInBatch(Iterable<CartDetail> cartDetails);
}
