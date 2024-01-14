package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.OrderRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;

public interface BillService {

    Bill createNewBill(int customerId);

    BillDetail createNewBillDetail(int customerId, Product product);

    CommonResponseDto saveOrder(int customerId, OrderRequestDto requestDto);

}
