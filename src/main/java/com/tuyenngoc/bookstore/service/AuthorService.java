package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthor(int authorId);

    PaginationResponseDto<Author> getAuthors(PaginationFullRequestDto requestDto);

    Author createAuthor(AuthorDto authorDto);

    CommonResponseDto deleteAuthor(int authorId);

}
