package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.entity.BookSet;

import java.util.List;

public interface BookSetService {

    List<BookSet> getAllBookSets();

    BookSet getBookSet(int bookSetId);
}
