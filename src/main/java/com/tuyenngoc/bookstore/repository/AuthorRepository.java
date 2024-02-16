package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    boolean existsById(int authorId);

    void deleteById(int authorId);

    boolean existsByFullName(String fullName);

    boolean existsByFullNameAndIdNot(String fullName, int authorId);
}
