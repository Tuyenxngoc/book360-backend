package com.tuyenngoc.bookstore.domain.dto.pagination;

import com.tuyenngoc.bookstore.constant.CommonConstant;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationRequestDto {

    @Parameter(description = "Page you want to retrieve (0..N)")
    private Integer pageNum = CommonConstant.PAGE_NUM_DEFAULT;

    @Parameter(description = "Number of records per page")
    private Integer pageSize = CommonConstant.PAGE_SIZE_DEFAULT;

    public int getPageNum() {
        if (pageNum < 1) {
            pageNum = CommonConstant.PAGE_NUM_DEFAULT;
        }
        return pageNum - 1;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = CommonConstant.PAGE_SIZE_DEFAULT;
        }
        return pageSize;
    }

}
