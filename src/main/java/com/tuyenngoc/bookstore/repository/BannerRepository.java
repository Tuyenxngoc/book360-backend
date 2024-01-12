package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Page<Banner> findAll(Pageable pageable);

}
