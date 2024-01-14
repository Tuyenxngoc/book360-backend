package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.request.OrderRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.mapper.BillMapper;
import com.tuyenngoc.bookstore.repository.*;
import com.tuyenngoc.bookstore.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    private final ProductRepository productRepository;

    private final BillRepository billRepository;

    private final BillDetailRepository billDetailRepository;

    private final BillMapper billMapper;

    private final MessageSource messageSource;

    @Override
    public Bill createNewBill(int customerId) {
        return null;
    }

    @Override
    public BillDetail createNewBillDetail(int customerId, Product product) {
        return null;
    }

    @Override
    public CommonResponseDto saveOrder(int customerId, OrderRequestDto requestDto) {
        String message = messageSource.getMessage(SuccessMessage.Bill.SAVE_ORDER, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

}
