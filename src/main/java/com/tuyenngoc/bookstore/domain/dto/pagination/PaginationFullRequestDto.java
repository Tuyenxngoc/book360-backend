package com.tuyenngoc.bookstore.domain.dto.pagination;

import com.tuyenngoc.bookstore.constant.CommonConstant;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationFullRequestDto extends PaginationSortRequestDto {

    @Parameter(description = "Keyword to search")
    private String keyword = CommonConstant.EMPTY_STRING;

    @Parameter(description = "Search by")
    private String searchBy = CommonConstant.EMPTY_STRING;

    public String getKeyword() {
        return keyword.trim();
    }

    public String getSearchBy() {
        return searchBy.trim();
    }
}
