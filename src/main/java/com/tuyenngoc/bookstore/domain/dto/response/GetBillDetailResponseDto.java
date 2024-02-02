package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.domain.dto.BillDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBillDetailResponseDto {

    private Integer id;

    private String consigneeName;

    private LocalDateTime createdDate;

    private String shippingAddress;

    private String phoneNumber;

    private String email;

    private double shippingFee;

    private double totalAmount;

    private String paymentMethod;

    private String billStatus;

    private String note;

    private List<BillDetailDto> billDetails = new ArrayList<>();

}
