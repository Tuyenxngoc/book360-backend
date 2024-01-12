package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.BannerDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @Operation(summary = "API get all banners")
    @GetMapping(UrlConstant.Banner.GET_ALL_BANNERS)
    public ResponseEntity<?> getAllBanners() {
        return VsResponseUtil.success(bannerService.getAllBanners());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get banner")
    @GetMapping(UrlConstant.Banner.GET_BANNER)
    public ResponseEntity<?> getBanner(@PathVariable int bannerId) {
        return VsResponseUtil.success(bannerService.getBanner(bannerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get banners")
    @GetMapping(UrlConstant.Banner.GET_BANNERS)
    public ResponseEntity<?> getBanners(@ParameterObject @Valid PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(bannerService.getBanners(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API create banner")
    @PostMapping(value = UrlConstant.Banner.CREATE_BANNER)
    public ResponseEntity<?> createBanner(@Valid @RequestBody BannerDto banner) {
        return VsResponseUtil.success(bannerService.createBanner(banner));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API update banner")
    @PutMapping(value = UrlConstant.Banner.UPDATE_BANNER)
    public ResponseEntity<?> updateBanner(@PathVariable int bannerId, @Valid @RequestBody BannerDto banner) {
        return VsResponseUtil.success(bannerService.updateBanner(bannerId, banner));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API delete banner")
    @DeleteMapping(UrlConstant.Banner.DELETE_BANNER)
    public ResponseEntity<?> deleteProductFromCart(@PathVariable int bannerId) {
        return VsResponseUtil.success(bannerService.deleteBanner(bannerId));
    }
}
