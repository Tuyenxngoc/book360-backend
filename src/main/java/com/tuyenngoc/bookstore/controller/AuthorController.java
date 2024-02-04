package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.service.AuthorService;
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
@Tag(name = "Author")
public class AuthorController {

    private final AuthorService authorService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get all authors")
    @GetMapping(UrlConstant.Author.GET_ALL_AUTHORS)
    public ResponseEntity<?> getAllAuthors() {
        return VsResponseUtil.success(authorService.getAllAuthors());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get author")
    @GetMapping(UrlConstant.Author.GET_AUTHOR)
    public ResponseEntity<?> getAuthor(@PathVariable int authorId) {
        return VsResponseUtil.success(authorService.getAuthor(authorId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get authors")
    @GetMapping(UrlConstant.Author.GET_AUTHORS)
    public ResponseEntity<?> getAuthors(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(authorService.getAuthors(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API create and update author")
    @PutMapping(value = UrlConstant.Author.CREATE_AUTHOR)
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return VsResponseUtil.success(authorService.createAuthor(authorDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API delete author")
    @DeleteMapping(UrlConstant.Author.DELETE_AUTHOR)
    public ResponseEntity<?> deleteAuthor(@PathVariable int authorId) {
        return VsResponseUtil.success(authorService.deleteAuthor(authorId));
    }
}
