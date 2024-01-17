package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.request.OrderRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetCountBillResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;

public interface BillService {

    Bill createNewBill(int customerId, OrderRequestDto requestDto);

    BillDetail createNewBillDetail(int customerId, Product product, int quantity, Bill bill);

    CommonResponseDto saveOrder(int customerId, OrderRequestDto requestDto);

    PaginationResponseDto<GetBillResponseDto> getBills(int customerId, PaginationFullRequestDto requestDto, String billStatus);

    CommonResponseDto cancelOrder(int customerId, int billId);

    GetCountBillResponseDto getCountBillsByStatus(int customerId);
}
