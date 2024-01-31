package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.domain.entity.BookSet;
import com.tuyenngoc.bookstore.repository.BookSetRepository;
import com.tuyenngoc.bookstore.service.BookSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSetServiceImpl implements BookSetService {

    private final BookSetRepository bookSetRepository;

    @Override
    public List<BookSet> getAllBookSets() {
        return bookSetRepository.findAll();
    }

}
