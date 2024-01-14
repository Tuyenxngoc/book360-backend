package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE CartDetail cd SET cd.quantity=:quantity WHERE cd.cart.id=:cartId AND cd.product.id=:productId")
    void updateCartDetail(@Param("cartId") int cartId,
                          @Param("productId") int productId,
                          @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartDetail cd WHERE cd.cart.id=:cartId AND cd.product.id=:productId")
    void deleteCartDetail(@Param("cartId") int cartId,
                          @Param("productId") int productId);

    Optional<CartDetail> findByCartIdAndProductId(int cartId, int productId);

    @Query("SELECT cd.product FROM CartDetail cd WHERE cd.cart.id = :cartId AND cd.product.id=:productId")
    Optional<Product> findProduct(@Param("cartId") int cartId,
                                  @Param("productId") int productId);
}
