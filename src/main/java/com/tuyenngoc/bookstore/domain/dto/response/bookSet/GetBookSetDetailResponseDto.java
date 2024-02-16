package com.tuyenngoc.bookstore.domain.dto.response.bookSet;

import com.tuyenngoc.bookstore.domain.dto.common.UserDateAuditingDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductBasicInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetBookSetDetailResponseDto extends UserDateAuditingDto {

    private int id;

    private String name;

    private List<GetProductBasicInfoResponseDto> products = new ArrayList<>();

}
