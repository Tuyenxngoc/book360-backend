package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>, JpaSpecificationExecutor<Bill> {

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto(b) " +
            "FROM Bill b WHERE " +
            "b.customer.id=:customerId ")
    List<GetBillResponseDto> getBills(
            @Param("customerId") int customerId
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto(b) " +
            "FROM Bill b WHERE " +
            "b.customer.id=:customerId AND " +
            "b.billStatus= :billStatus")
    List<GetBillResponseDto> getBills(
            @Param("customerId") int customerId,
            @Param("billStatus") BillStatus billStatus
    );

    @Query("SELECT b.billStatus" +
            " FROM Bill b WHERE " +
            "b.id =:billId AND " +
            "b.customer.id =:customerId")
    BillStatus getBillStatus(
            @Param("billId") int billId,
            @Param("customerId") int customerId
    );

    @Query("SELECT b.billStatus" +
            " FROM Bill b WHERE " +
            "b.id =:billId ")
    BillStatus getBillStatus(@Param("billId") int billId);

    @Modifying
    @Transactional
    @Query("UPDATE Bill b " +
            "SET b.billStatus=:billStatus WHERE " +
            "b.id =:billId")
    void updateBillStatus(
            @Param("billId") int billId,
            @Param("billStatus") BillStatus billStatus
    );

    @Query("SELECT count(b) " +
            "FROM Bill b WHERE " +
            "b.billStatus=:status")
    int getCountBillByStatus(@Param("status") BillStatus status);

    @Query("SELECT count(b) " +
            "FROM Bill b WHERE " +
            "b.billStatus=:status AND " +
            "b.customer.id =:customerId")
    int getCountBillByStatusAndCustomerId(
            @Param("customerId") int customerId,
            @Param("status") BillStatus status
    );

    Optional<Bill> getBillByIdAndCustomerId(int customerId, int billId);
}
