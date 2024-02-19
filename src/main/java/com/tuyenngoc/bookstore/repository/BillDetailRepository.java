package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {

    @Query("SELECT SUM(b.quantity) " +
            "FROM BillDetail b WHERE " +
            "b.createdDate >= :startTime AND b.createdDate <= :endTime")
    int getCountProductSoldBetween(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}
