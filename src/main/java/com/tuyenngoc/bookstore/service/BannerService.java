package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.BannerDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Banner;

import java.util.List;

public interface BannerService {

    List<BannerDto> getAllBanners();

    PaginationResponseDto<Banner> getAllBanners(PaginationFullRequestDto requestDto);

    Banner getBanner(int bannerId);

    CommonResponseDto deleteBanner(int bannerId);

    Banner createBanner(BannerDto bannerDto);
}
