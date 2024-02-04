package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.BookSetDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.service.BookSetService;
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
@Tag(name = "Book set")
public class BookSetController {

    private final BookSetService bookSetService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get all book sets")
    @GetMapping(UrlConstant.BookSet.GET_ALL_BOOK_SETS)
    public ResponseEntity<?> getAllBookSets() {
        return VsResponseUtil.success(bookSetService.getAllBookSets());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get book sets")
    @GetMapping(UrlConstant.BookSet.GET_BOOK_SETS)
    public ResponseEntity<?> getBookSets(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(bookSetService.getBookSets(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get book set")
    @GetMapping(UrlConstant.BookSet.GET_BOOK_SET)
    public ResponseEntity<?> getBookSet(@PathVariable int bookSetId) {
        return VsResponseUtil.success(bookSetService.getBookSet(bookSetId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API create and update book set")
    @PutMapping(UrlConstant.BookSet.CREATE_BOOK_SET)
    public ResponseEntity<?> createBookSet(@Valid @RequestBody BookSetDto bookSetDto) {
        return VsResponseUtil.success(bookSetService.createBookSet(bookSetDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API delete book set")
    @DeleteMapping(UrlConstant.BookSet.DELETE_BOOK_SET)
    public ResponseEntity<?> deleteBookSet(@PathVariable int bookSetId) {
        return VsResponseUtil.success(bookSetService.deleteBookSet(bookSetId));
    }

}
