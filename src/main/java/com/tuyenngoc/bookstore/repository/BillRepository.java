package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query("SELECT b.billStatus FROM Bill b WHERE b.id =:billId AND b.customer.id =:customerId")
    String getBillStatusByIdAndCustomerId(
            @Param("billId") int billId,
            @Param("customerId") int customerId
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto(b) FROM Bill b WHERE b.customer.id=:customerId")
    Page<GetBillResponseDto> getBills(
            @Param("customerId") int customerId,
            Pageable pageable
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto(b) FROM Bill b WHERE b.customer.id=:customerId AND b.billStatus= :billStatus")
    Page<GetBillResponseDto> getBills(
            @Param("customerId") int customerId,
            @Param("billStatus") String billStatus,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("UPDATE Bill b SET b.billStatus=:billStatus WHERE b.id =:billId")
    void updateBillStatus(
            @Param("billId") int billId,
            @Param("billStatus") String billStatus
    );

    @Query("SELECT count(b) FROM Bill b WHERE b.billStatus=:status")
    int getCountBillByStatus(@Param("status") String status);

    @Query("SELECT count(b) FROM Bill b WHERE b.billStatus=:status AND b.customer.id =:customerId")
    int getCountBillByStatusAndCustomerId(@Param("status") String status,
                                          @Param("customerId") int customerId
    );

    @Query("SELECT count(b) FROM Bill b")
    int getCountBills();

    @Query("SELECT b FROM Bill b JOIN b.billDetails bd WHERE bd.product.name LIKE %:keyword% AND b.billStatus=:status")
    Page<Bill> getBillsByProductName(
            @Param("status") String status,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT b FROM Bill b WHERE b.consigneeName LIKE %:keyword% AND b.billStatus=:status")
    Page<Bill> getBillsByConsigneeName(
            @Param("status") String status,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT b FROM Bill b WHERE b.id =:billId AND b.billStatus=:status")
    Page<Bill> getBillsById(
            @Param("status") String status,
            @Param("billId") Integer billId,
            Pageable pageable
    );

    @Query("SELECT b FROM Bill b WHERE b.billStatus=:status")
    Page<Bill> getBills(
            @Param("status") String status,
            Pageable pageable
    );
}
