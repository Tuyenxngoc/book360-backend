package com.tuyenngoc.bookstore.domain.dto.response.bill;

import com.tuyenngoc.bookstore.domain.dto.BillDetailDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBillResponseDto {

    private int id;

    private String shippingName;

    private LocalDateTime createdDate;

    private String billStatus;

    private double totalAmount;

    private List<BillDetailDto> billDetails = new ArrayList<>();

    public GetBillResponseDto(Bill bill) {
        this.id = bill.getId();
        this.shippingName = bill.getShippingName();
        this.createdDate = bill.getCreatedDate();
        this.billStatus = bill.getBillStatus().getName();
        this.totalAmount = bill.getTotalAmount();

        this.billDetails = getBillDetailDto(bill.getBillDetails());
    }

    private List<BillDetailDto> getBillDetailDto(List<BillDetail> billDetails) {
        return billDetails.stream()
                .map(BillDetailDto::new)
                .collect(Collectors.toList());
    }

}
