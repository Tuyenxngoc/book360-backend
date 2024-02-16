package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.BookSetDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.bookSet.GetBookSetDetailResponseDto;
import com.tuyenngoc.bookstore.domain.entity.BookSet;
import com.tuyenngoc.bookstore.domain.mapper.BookSetMapper;
import com.tuyenngoc.bookstore.exception.DataIntegrityViolationException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.BookSetRepository;
import com.tuyenngoc.bookstore.service.BookSetService;
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
public class BookSetServiceImpl implements BookSetService {

    private final BookSetRepository bookSetRepository;

    private final MessageSource messageSource;

    private final BookSetMapper bookSetMapper;

    @Override
    public List<BookSet> getAllBookSets() {
        return bookSetRepository.findAll();
    }

    @Override
    public BookSet getBookSet(int bookSetId) {
        return bookSetRepository.findById(bookSetId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BookSet.ERR_NOT_FOUND_ID, String.valueOf(bookSetId)));
    }

    @Override
    public PaginationResponseDto<BookSet> getBookSets(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BOOK_SET);

        Page<BookSet> page = bookSetRepository.findAll(pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.BOOK_SET, page);

        PaginationResponseDto<BookSet> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public BookSet createBookSet(BookSetDto bookSetDto) {
        BookSet bookSet;
        if (bookSetDto.getId() == null) {
            // check if name already exists
            boolean isNameExists = bookSetRepository.existsByName(bookSetDto.getName());
            if (isNameExists) {
                throw new DataIntegrityViolationException(ErrorMessage.BookSet.ERR_DUPLICATE_NAME, bookSetDto.getName());
            }
            // mapping book set
            bookSet = bookSetMapper.toBookSet(bookSetDto);
        } else {
            // check if name exists excluding the current book set
            boolean isNameExists = bookSetRepository.existsByNameAndIdNot(bookSetDto.getName(), bookSetDto.getId());
            if (isNameExists) {
                throw new DataIntegrityViolationException(ErrorMessage.BookSet.ERR_DUPLICATE_NAME, bookSetDto.getName());
            }
            // get book set
            bookSet = getBookSet(bookSetDto.getId());
            // set new values
            bookSet.setName(bookSetDto.getName());
        }
        return bookSetRepository.save(bookSet);
    }

    @Override
    public CommonResponseDto deleteBookSet(int bookSetId) {
        BookSet bookSet = getBookSet(bookSetId);

        if (bookSet.getProducts().size() > 0) {
            throw new DataIntegrityViolationException(ErrorMessage.BookSet.ERR_CANNOT_DELETE, String.valueOf(bookSetId));
        }

        bookSetRepository.delete(bookSet);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public GetBookSetDetailResponseDto getBookSetDetail(int bookSetId) {
        BookSet bookSet = getBookSet(bookSetId);
        return bookSetMapper.toGetBookSetDetailResponseDto(bookSet);
    }

}
