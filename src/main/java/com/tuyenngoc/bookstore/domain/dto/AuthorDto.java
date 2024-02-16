package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.domain.entity.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Integer id;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String fullName;

    @Size(min = 0, max = 3000, message = ErrorMessage.INVALID_TEXT_LENGTH)
    private String biography;

    @URL(message = ErrorMessage.INVALID_URL_FORMAT)
    private String avatar;

    public AuthorDto(Author author) {
        this.id = author.getId();
        this.fullName = author.getFullName();
    }
}
