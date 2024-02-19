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
    private Integer sellerStockMax;

    @Parameter(description = "Minimum seller stock quantity")
    private Integer sellerStockMin;

    @Parameter(description = "Maximum quantity sold")
    private Integer soldMax;

    @Parameter(description = "Minimum quantity sold")
    private Integer soldMin;

    @Parameter(description = "Filter by category ID")
    private Integer categoryId;

}
