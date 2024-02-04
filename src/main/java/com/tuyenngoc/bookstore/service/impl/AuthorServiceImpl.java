package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.mapper.AuthorMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.AuthorRepository;
import com.tuyenngoc.bookstore.service.AuthorService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    private final MessageSource messageSource;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthor(int authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Author.ERR_NOT_FOUND_ID, String.valueOf(authorId)));
    }

    @Override
    public PaginationResponseDto<Author> getAuthors(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.AUTHOR);

        Page<Author> page = authorRepository.findAll(pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.AUTHOR, page);

        PaginationResponseDto<Author> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public Author createAuthor(AuthorDto authorDto) {
        Author author;
        if (authorDto.getId() == null) {
            author = authorMapper.toAuthor(authorDto);
        } else {
            author = getAuthor(authorDto.getId());

            //Set new values
            author.setFullName(authorDto.getFullName());
        }
        return authorRepository.save(author);
    }

    @Override
    public CommonResponseDto deleteAuthor(int authorId) {
        if (authorRepository.existsById(authorId)) {
            authorRepository.deleteById(authorId);
            String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(message);
        } else {
            throw new NotFoundException(ErrorMessage.Author.ERR_NOT_FOUND_ID, String.valueOf(authorId));
        }
    }
}
