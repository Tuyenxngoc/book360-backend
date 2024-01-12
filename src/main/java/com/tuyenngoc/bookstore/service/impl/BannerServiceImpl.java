package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.BannerDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Banner;
import com.tuyenngoc.bookstore.domain.mapper.BannerMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.BannerRepository;
import com.tuyenngoc.bookstore.service.BannerService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import com.tuyenngoc.bookstore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    private final UploadFileUtil uploadFileUtil;

    private final MessageSource messageSource;

    private final BannerMapper bannerMapper;

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public PaginationResponseDto<Banner> getBanners(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BANNER);

        Page<Banner> page = bannerRepository.findAll(pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.BANNER, page);

        PaginationResponseDto<Banner> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public Banner getBanner(int bannerId) {
        return bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));
    }

    @Override
    public CommonResponseDto updateBanner(int bannerId, BannerDto bannerDto) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)}));

        uploadFileUtil.destroyFileWithUrl(banner.getImage());

        banner.setImage(bannerDto.getImage());
        banner.setUrl(bannerDto.getUrl());
        banner.setViewOrder(bannerDto.getViewOrder());

        bannerRepository.save(banner);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto deleteBanner(int bannerId) {
        if (bannerRepository.existsById(bannerId)) {
            bannerRepository.deleteById(bannerId);
            return new CommonResponseDto(SuccessMessage.DELETE);
        } else {
            throw new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerId)});
        }
    }

    @Override
    public Banner createBanner(BannerDto bannerDto) {
        Banner banner;
        if (bannerDto.getId() == -1) {
            banner = bannerMapper.toBanner(bannerDto);
        } else {
            banner = bannerRepository.findById(bannerDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Banner.ERR_NOT_FOUND_ID, new String[]{String.valueOf(bannerDto.getId())}));

            uploadFileUtil.destroyFileWithUrl(banner.getImage());

            banner.setImage(bannerDto.getImage());
            banner.setUrl(bannerDto.getUrl());
            banner.setViewOrder(bannerDto.getViewOrder());
        }
        return bannerRepository.save(banner);
    }
}
