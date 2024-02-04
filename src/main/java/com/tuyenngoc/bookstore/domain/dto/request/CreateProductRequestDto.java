package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.AgeGroup;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    private Integer id;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Size(min = 10, max = 120, message = ErrorMessage.INVALID_TEXT_LENGTH)
    private String name;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Size(min = 10, max = 3000, message = ErrorMessage.INVALID_TEXT_LENGTH)
    private String description;

    private String isbn;

    private String publisher;

    private String size;

    private String coverType;

    private Set<
            @NotNull(message = ErrorMessage.INVALID_NOT_NULL_FIELD)
                    AgeGroup
            > ageClassifications = new HashSet<>();

    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Double weight;

    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Integer pageCount;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer categoryId;

    private Integer bookSetId;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Integer stockQuantity;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Double price;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Integer discount;

    @NotNull(message = ErrorMessage.INVALID_ARRAY_IS_REQUIRED)
    @NotEmpty(message = ErrorMessage.INVALID_ARRAY_NOT_EMPTY)
    private List<
            @URL(message = ErrorMessage.INVALID_URL_FORMAT)
            @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
                    String
            > imageURLs = new ArrayList<>();

    @NotNull(message = ErrorMessage.INVALID_ARRAY_IS_REQUIRED)
    @NotEmpty(message = ErrorMessage.INVALID_ARRAY_NOT_EMPTY)
    private Set<
            @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
                    Integer
            > authorIds = new HashSet<>();

}
