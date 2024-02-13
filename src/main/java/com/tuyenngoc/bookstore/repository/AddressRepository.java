package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findByLatitudeAndLongitude(double latitude, double longitude);

}
