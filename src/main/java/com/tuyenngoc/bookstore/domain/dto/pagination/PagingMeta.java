package com.tuyenngoc.bookstore.domain.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagingMeta {

    private Long totalElements;

    private Integer totalPages;

    private Integer pageNum;

    private Integer pageSize;

    private String sortBy;

    private String sortType;

    private String keyword;

    private String searchBy;
}