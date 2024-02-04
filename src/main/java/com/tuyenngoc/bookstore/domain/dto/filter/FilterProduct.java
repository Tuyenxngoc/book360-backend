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
public class FilterProduct {

    @Parameter(description = "Maximum seller stock quantity")
    Integer sellerStockMax;

    @Parameter(description = "Minimum seller stock quantity")
    Integer sellerStockMin;

    @Parameter(description = "Maximum quantity sold")
    Integer soldMax;

    @Parameter(description = "Minimum quantity sold")
    Integer soldMin;

    @Parameter(description = "Filter by category ID")
    Integer categoryId;

}
