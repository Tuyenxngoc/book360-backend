package com.tuyenngoc.bookstore.domain.dto.response.bill;

import com.tuyenngoc.bookstore.domain.dto.BillDetailDto;
import com.tuyenngoc.bookstore.domain.dto.common.DateAuditingDto;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import jakarta.persistence.Column;
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
public class GetBillDetailResponseDto extends DateAuditingDto {

    private Integer id;

    private String shippingName;

    private String shippingPhone;

    private String shippingAddress;

    private double shippingFee;

    private double totalAmount;

    private String paymentMethod;

    private String billStatus;

    private String note;

    private List<BillDetailDto> billDetails = new ArrayList<>();

}
