package com.tuyenngoc.bookstore.domain.dto.filter;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterBook {

    @Parameter(description = "Maximum price of the book")
    private Double priceMax;

    @Parameter(description = "Minimum price of the book")
    private Double priceMin;

    @Parameter(description = "Book category")
    private Integer categoryId;

    @Parameter(description = "Book set")
    private Integer bookSetId;

    @Parameter(description = "Book author")
    private Integer authorId;

}