package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.domain.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private int id;

    private String fullName;

    public AuthorDto(Author author) {
        this.id = author.getId();
        this.fullName = author.getFullName();
    }
}
