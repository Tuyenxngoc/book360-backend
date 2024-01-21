package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.BannerDto;
import com.tuyenngoc.bookstore.domain.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Page<Banner> findAll(Pageable pageable);

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.BannerDto(b) FROM Banner b")
    List<BannerDto> getBanners();
}
