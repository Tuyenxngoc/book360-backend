package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthor(int authorId);

}
