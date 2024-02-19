package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.request.BillRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.bill.GetBillDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.bill.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.statistics.SalesStatistics;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;

import java.util.List;

public interface BillService {

    Bill createNewBill(int customerId, BillRequestDto requestDto);

    BillDetail createNewBillDetail(Product product, int quantity, Bill bill);

    List<GetBillResponseDto> getBills(int customerId, BillStatus billStatus);

    CommonResponseDto saveOrder(int customerId, BillRequestDto requestDto);

    double calculatePrice(double price, int discount, int quantity);

    CommonResponseDto cancelOrder(int customerId, int billId, String cancellationReason);

    int getCountBillByStatus(int customerId, BillStatus billStatus);

    Long getCountBills();

    Bill getBill(int billId);

    PaginationResponseDto<Bill> getBillsForAdmin(PaginationFullRequestDto requestDto, BillStatus billStatus);

    CommonResponseDto updateBillStatus(int billId, BillStatus newStatus);

    GetBillDetailResponseDto getBillDetail(int customerId, int billId);

    SalesStatistics getKeyMetrics(String orderType);

}
