package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.service.BookSetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiV1
@RequiredArgsConstructor
public class BookSetController {

    private final BookSetService bookSetService;

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Book set controller admin")
    @Operation(summary = "API get all book sets")
    @GetMapping(UrlConstant.BookSet.GET_ALL_BOOK_SETS)
    public ResponseEntity<?> getAllBookSets() {
        return VsResponseUtil.success(bookSetService.getAllBookSets());
    }

}
