package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.author.GetAuthorDetailResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.mapper.AuthorMapper;
import com.tuyenngoc.bookstore.exception.DataIntegrityViolationException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.AuthorRepository;
import com.tuyenngoc.bookstore.service.AuthorService;
import com.tuyenngoc.bookstore.service.UploadRedisService;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    private final MessageSource messageSource;

    private final UploadRedisService uploadRedisService;

    private final UploadFileUtil uploadFileUtil;

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
    public Author createAuthor(AuthorDto authorDto, String username) {
        Author author;
        if (authorDto.getId() == null) {
            // check if full name already exists
            boolean isFullNameExists = authorRepository.existsByFullName(authorDto.getFullName());
            if (isFullNameExists) {
                throw new DataIntegrityViolationException(ErrorMessage.Author.ERR_DUPLICATE_NAME, authorDto.getFullName());
            }
            // mapping author
            author = authorMapper.toAuthor(authorDto);
        } else {
            // check if full name exists excluding the current author
            boolean isFullNameExists = authorRepository.existsByFullNameAndIdNot(authorDto.getFullName(), authorDto.getId());
            if (isFullNameExists) {
                throw new DataIntegrityViolationException(ErrorMessage.Author.ERR_DUPLICATE_NAME, authorDto.getFullName());
            }
            // get author
            author = getAuthor(authorDto.getId());
            // set new values
            author.setFullName(authorDto.getFullName());
            if (authorDto.getBiography() != null) {
                author.setBiography(authorDto.getBiography());
            }
            if (authorDto.getAvatar() != null) {
                author.setAvatar(authorDto.getAvatar());
            }
        }
        //Delete image urls from redis cache
        if (authorDto.getAvatar() != null) {
            uploadRedisService.deleteUrlFromRedisList(username, authorDto.getAvatar());
        }

        return authorRepository.save(author);
    }

    @Override
    public CommonResponseDto deleteAuthor(int authorId) {
        Author author = getAuthor(authorId);

        if (author.getProducts().size() > 0) {
            throw new DataIntegrityViolationException(ErrorMessage.Author.ERR_CANNOT_DELETE, String.valueOf(authorId));
        }

        uploadFileUtil.destroyFileWithUrl(author.getAvatar());

        authorRepository.delete(author);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public GetAuthorDetailResponseDto getAuthorDetail(int authorId) {
        Author author = getAuthor(authorId);
        return authorMapper.toGetAuthorDetailResponseDto(author);
    }
}
