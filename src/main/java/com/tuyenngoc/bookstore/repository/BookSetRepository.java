package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.entity.BookSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSetRepository extends JpaRepository<BookSet, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, int bookSetId);
}
