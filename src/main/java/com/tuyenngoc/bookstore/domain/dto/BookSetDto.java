package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.domain.entity.BookSet;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSetDto {

    private Integer id;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String name;

    public BookSetDto(BookSet bookSet) {
        this.id = bookSet.getId();
        this.name = bookSet.getName();
    }

}
