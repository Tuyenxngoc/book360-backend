package com.tuyenngoc.bookstore.domain.dto.response.author;

import com.tuyenngoc.bookstore.domain.dto.common.UserDateAuditingDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductBasicInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAuthorDetailResponseDto extends UserDateAuditingDto {

    private Integer id;

    private String fullName;

    private String biography;

    private String avatar;

    private List<GetProductBasicInfoResponseDto> products = new ArrayList<>();

}
