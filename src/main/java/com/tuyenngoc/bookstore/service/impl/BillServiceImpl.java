package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.*;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.request.BillRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetCountBillResponseDto;
import com.tuyenngoc.bookstore.domain.entity.*;
import com.tuyenngoc.bookstore.domain.mapper.BillMapper;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.*;
import com.tuyenngoc.bookstore.service.BillService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Bill createNewBill(int customerId, BillRequestDto requestDto) {
        Customer customer = new Customer();
        customer.setId(customerId);

        Bill newBill = billMapper.toBill(requestDto);
        newBill.setShippingFee(30000);
        newBill.setBillStatus(BillStatus.WAIT_FOR_CONFIRMATION.getName());
        newBill.setCustomer(customer);
        return newBill;
    }

    @Override
    public BillDetail createNewBillDetail(int customerId, Product product, int quantity, Bill bill) {
        BillDetail newBillDetail = new BillDetail();
        newBillDetail.setProduct(product);
        newBillDetail.setQuantity(quantity);
        newBillDetail.setPrice(product.getPrice());
        newBillDetail.setBill(bill);
        return newBillDetail;
    }

    @Override
    public CommonResponseDto saveOrder(int customerId, BillRequestDto requestDto) {
        //Validate
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_CUSTOMER_ID, String.valueOf(customerId)));
        List<Integer> listProductIds = requestDto.getListProductId();
        List<CartDetail> cartDetails = cart.getCartDetails()
                .stream()
                .filter(cartDetail -> listProductIds.contains(cartDetail.getProduct().getId()))
                .collect(Collectors.toList());
        if (cartDetails.size() == 0) {
            throw new InvalidException(ErrorMessage.Cart.ERR_NOT_FOUND_PRODUCT_ID, listProductIds.toString());
        }
        //Create a new Bill
        Bill newBill = createNewBill(customerId, requestDto);

        double totalPrice = 30000;
        List<BillDetail> billDetails = new ArrayList<>();

        for (CartDetail cartDetail : cartDetails) {
            //Get the total price
            Product product = cartDetail.getProduct();
            totalPrice += calculatePrice(product.getPrice(), product.getDiscount(), cartDetail.getQuantity());
            //Add new bill detail to list
            BillDetail newBillDetail = createNewBillDetail(customerId, product, cartDetail.getQuantity(), newBill);
            billDetails.add(newBillDetail);
        }
        newBill.setTotalAmount(totalPrice);
        newBill.setBillDetails(billDetails);

        cartDetailRepository.deleteAllInBatch(cartDetails);
        billRepository.save(newBill);

        String message = messageSource.getMessage(SuccessMessage.Bill.SAVE_ORDER, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    private double calculatePrice(double price, int discount, int quantity) {
        double discountedPrice = price * (1 - discount / 100.0);
        return discountedPrice * quantity;
    }

    @Override
    public PaginationResponseDto<GetBillResponseDto> getBills(int customerId, PaginationFullRequestDto requestDto, String billStatus) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BILL);

        Page<GetBillResponseDto> page;
        String billStatusName = BillStatus.getByKey(billStatus);
        if (billStatusName == null) {
            page = billRepository.getBills(customerId, pageable);
        } else {
            page = billRepository.getBills(customerId, billStatusName, pageable);
        }

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.BILL, page);

        PaginationResponseDto<GetBillResponseDto> responseDto = new PaginationResponseDto<>();

        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public CommonResponseDto cancelOrder(int customerId, int billId) {
        String billStatus = billRepository.getBillStatusByIdAndCustomerId(billId, customerId);

        if (billStatus == null) {
            throw new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId));
        }

        if (!billStatus.equals(BillStatus.CANCELLED.getName())) {
            if (!billStatus.equals(BillStatus.WAIT_FOR_CONFIRMATION.getName())) {
                throw new InvalidException(ErrorMessage.Bill.ERR_NOT_ALLOW_CANCEL);
            }
            billRepository.updateBillStatus(billId, BillStatus.CANCELLED.getName());
        }

        String message = messageSource.getMessage(SuccessMessage.Bill.CANCEL_ORDER, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public GetCountBillResponseDto getCountBillsByStatus(int customerId) {
        int unpaid = billRepository.getCountBillByStatusAndCustomerId(BillStatus.WAIT_FOR_CONFIRMATION.getName(), customerId);
        int toShip = billRepository.getCountBillByStatusAndCustomerId(BillStatus.WAIT_FOR_DELIVERY.getName(), customerId);
        int shipping = billRepository.getCountBillByStatusAndCustomerId(BillStatus.DELIVERING.getName(), customerId);
        int completed = billRepository.getCountBillByStatusAndCustomerId(BillStatus.DELIVERED.getName(), customerId);
        int cancelled = billRepository.getCountBillByStatusAndCustomerId(BillStatus.CANCELLED.getName(), customerId);
        int refund = billRepository.getCountBillByStatusAndCustomerId(BillStatus.REFUND.getName(), customerId);

        return new GetCountBillResponseDto(unpaid, toShip, shipping, completed, cancelled, refund);
    }

    @Override
    public int getCountBills() {
        return billRepository.getCountBills();
    }

    @Override
    public PaginationResponseDto<Bill> getAllBills(PaginationFullRequestDto requestDto, String billStatus) {

        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BILL);

        String searchBy = requestDto.getSearchBy();

        Page<Bill> page;
        if (searchBy == null || searchBy.isEmpty()) {
            page = billRepository.getBills(billStatus, pageable);
        } else if (searchBy.equals(SearchByConstant.Bill.BILL_ID)) {
            try {
                int id = Integer.parseInt(requestDto.getKeyword());
                page = billRepository.getBillsById(billStatus, id, pageable);
            } catch (Exception e) {
                page = billRepository.getBillsById(billStatus, null, pageable);
            }
        } else if (searchBy.equals(SearchByConstant.Bill.CUSTOMER_NAME)) {
            page = billRepository.getBillsByConsigneeName(billStatus, requestDto.getKeyword(), pageable);
        } else if (searchBy.equals(SearchByConstant.Bill.PRODUCT_NAME)) {
            page = billRepository.getBillsByProductName(billStatus, requestDto.getKeyword(), pageable);
        } else {
            page = billRepository.getBills(billStatus, pageable);
        }

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.BILL, page);

        PaginationResponseDto<Bill> responseDto = new PaginationResponseDto<>();

        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

}
