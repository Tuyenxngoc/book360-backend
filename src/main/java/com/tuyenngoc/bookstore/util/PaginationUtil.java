package com.tuyenngoc.bookstore.util;

import com.tuyenngoc.bookstore.constant.CommonConstant;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationSortRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    public static Pageable buildPageable(PaginationRequestDto request) {
        return PageRequest.of(request.getPageNum(), request.getPageSize());
    }

    public static Pageable buildPageable(PaginationSortRequestDto requestDto, SortByDataConstant constant) {
        Sort sort;
        if (requestDto.getIsAscending()) {
            sort = Sort.by(requestDto.getSortBy(constant)).ascending();
        } else {
            sort = Sort.by(requestDto.getSortBy(constant)).descending();
        }
        return PageRequest.of(requestDto.getPageNum(), requestDto.getPageSize(), sort);
    }

    public static <T> PagingMeta buildPagingMeta(PaginationFullRequestDto request, SortByDataConstant constant, Page<T> pages) {
        return new PagingMeta(
                pages.getTotalElements(),
                pages.getTotalPages(),
                request.getPageNum() + CommonConstant.ONE_INT_VALUE,
                request.getPageSize(),
                request.getSortBy(constant),
                request.getIsAscending().equals(Boolean.TRUE) ? CommonConstant.SORT_TYPE_ASC : CommonConstant.SORT_TYPE_DESC,
                request.getKeyword(),
                request.getSearchBy()
        );
    }
}
