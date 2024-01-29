package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.AgeGroup;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String name;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String description;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = ErrorMessage.INVALID_LOCAL_DATE_FORMAT)
    private String publicationDate;

    private String isbn;

    private String publisher;

    private String language;

    private String format;

    private String size;

    private String coverType;

    @Schema(description = "[ADULT, YOUNG_ADULT, CHILD, ADOLESCENT, PRESCHOOL]")
    private Set<AgeGroup> ageClassifications = new HashSet<>();

    private String issuingUnit;

    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Double weight;

    @Min(value = 0, message = ErrorMessage.INVALID_MINIMUM_ZERO)
    private Integer pageCount;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer categoryId;

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
    private List<String> imageURLs = new ArrayList<>();

    @NotNull(message = ErrorMessage.INVALID_ARRAY_IS_REQUIRED)
    @NotEmpty(message = ErrorMessage.INVALID_ARRAY_NOT_EMPTY)
    private Set<Integer> authorIds = new HashSet<>();

}
