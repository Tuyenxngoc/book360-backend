package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.BannerDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<?> getBanners() {
        return VsResponseUtil.success(bannerService.getAllBanners());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Banner controller admin")
    @Operation(summary = "API get banner")
    @GetMapping(UrlConstant.Banner.GET_BANNER)
    public ResponseEntity<?> getBanner(@PathVariable int bannerId) {
        return VsResponseUtil.success(bannerService.getBanner(bannerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Banner controller admin")
    @Operation(summary = "API get banners")
    @GetMapping(UrlConstant.Banner.GET_BANNERS_FOR_ADMIN)
    public ResponseEntity<?> getBanners(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(bannerService.getAllBanners(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Banner controller admin")
    @Operation(summary = "API create and update banner")
    @PutMapping(value = UrlConstant.Banner.CREATE_BANNER)
    public ResponseEntity<?> createBanner(@Valid @RequestBody BannerDto banner) {
        return VsResponseUtil.success(bannerService.createBanner(banner));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Banner controller admin")
    @Operation(summary = "API delete banner")
    @DeleteMapping(UrlConstant.Banner.DELETE_BANNER)
    public ResponseEntity<?> deleteProductFromCart(@PathVariable int bannerId) {
        return VsResponseUtil.success(bannerService.deleteBanner(bannerId));
    }
}
