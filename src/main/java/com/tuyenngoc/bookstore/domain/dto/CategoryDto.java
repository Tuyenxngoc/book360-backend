package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int id;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String image;
}
