package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.request.BillRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;

import java.util.List;

public interface BillService {

    Bill createNewBill(int customerId, BillRequestDto requestDto);

    BillDetail createNewBillDetail(int customerId, Product product, int quantity, Bill bill);

    List<GetBillResponseDto> getBills(int customerId, BillStatus billStatus);

    CommonResponseDto saveOrder(int customerId, BillRequestDto requestDto);

    CommonResponseDto cancelOrder(int customerId, int billId);

    int getCountBillByStatus(int customerId, BillStatus billStatus);

    int getCountBills();

    Bill getBill(int billId);

    PaginationResponseDto<Bill> getBillsForAdmin(PaginationFullRequestDto requestDto, BillStatus billStatus);

    CommonResponseDto updateBillStatus(int billId, BillStatus newStatus);

    GetBillDetailResponseDto getBillDetail(int customerId, int billId);
}
