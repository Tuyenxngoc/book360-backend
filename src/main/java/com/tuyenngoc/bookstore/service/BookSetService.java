package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.BookSetDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.BookSet;

import java.util.List;

public interface BookSetService {

    List<BookSet> getAllBookSets();

    BookSet getBookSet(int bookSetId);

    PaginationResponseDto<BookSet> getBookSets(PaginationFullRequestDto requestDto);

    BookSet createBookSet(BookSetDto bookSetDto);

    CommonResponseDto deleteBookSet(int bookSetId);

}
