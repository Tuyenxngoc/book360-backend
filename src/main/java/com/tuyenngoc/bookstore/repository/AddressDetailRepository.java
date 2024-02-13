package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Integer> {
}
