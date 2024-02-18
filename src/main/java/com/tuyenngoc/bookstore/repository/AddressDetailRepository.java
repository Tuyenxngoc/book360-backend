package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.address.GetAddressResponseDto;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Integer> {

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.address.GetAddressResponseDto(a) " +
            "FROM AddressDetail a WHERE " +
            "a.customer.id=:customerId " +
            "ORDER BY a.isDefaultAddress DESC")
    List<GetAddressResponseDto> findAllByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.address.GetAddressResponseDto(a) " +
            "FROM AddressDetail a WHERE " +
            "a.customer.id=:customerId AND " +
            "a.id=:addressDetailId")
    Optional<GetAddressResponseDto> getAddressDetailByCustomerIdAndId(
            @Param("customerId") int customerId,
            @Param("addressDetailId") int addressDetailId
    );

    @Query("SELECT a.isDefaultAddress FROM AddressDetail a WHERE " +
            "a.customer.id=:customerId AND " +
            "a.id=:addressDetailId")
    Boolean getDefaultAddress(
            @Param("customerId") int customerId,
            @Param("addressDetailId") int addressDetailId
    );

    @Modifying
    @Transactional
    @Query("UPDATE AddressDetail a " +
            "SET a.isDefaultAddress = true WHERE " +
            "a.customer.id=:customerId AND " +
            "a.id=:addressDetailId")
    void setDeFaultAddress(
            @Param("customerId") int customerId,
            @Param("addressDetailId") int addressDetailId
    );

    @Modifying
    @Transactional
    @Query("UPDATE AddressDetail a " +
            "SET a.isDefaultAddress = false WHERE " +
            "a.customer.id=:customerId")
    void resetDeFaultAddress(@Param("customerId") int customerId);

    @Modifying
    @Transactional
    void deleteByCustomerIdAndId(int customerId, int addressDetailId);

    int countByCustomerId(int customerId);

    Optional<AddressDetail> findByCustomerIdAndId(int customerId, int addressDetailId);

}
