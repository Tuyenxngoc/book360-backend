package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.request.BillRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.entity.*;
import com.tuyenngoc.bookstore.domain.mapper.BillMapper;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.BillRepository;
import com.tuyenngoc.bookstore.repository.CartDetailRepository;
import com.tuyenngoc.bookstore.service.BillService;
import com.tuyenngoc.bookstore.service.CartService;
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

import static com.tuyenngoc.bookstore.specifications.BillSpecifications.filterBills;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final CartService cartService;

    private final CartDetailRepository cartDetailRepository;

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final MessageSource messageSource;

    @Override
    public Bill createNewBill(int customerId, BillRequestDto requestDto) {
        Customer customer = new Customer();
        customer.setId(customerId);

        Bill newBill = billMapper.toBill(requestDto);
        newBill.setShippingFee(30000);
        newBill.setBillStatus(BillStatus.WAIT_FOR_CONFIRMATION);
        newBill.setCustomer(customer);
        return newBill;
    }

    @Override
    public BillDetail createNewBillDetail(Product product, int quantity, Bill bill) {
        BillDetail newBillDetail = new BillDetail();
        newBillDetail.setProduct(product);
        newBillDetail.setQuantity(quantity);
        newBillDetail.setPrice(product.getPrice());
        newBillDetail.setBill(bill);
        return newBillDetail;
    }

    @Override
    public List<GetBillResponseDto> getBills(int customerId, BillStatus billStatus) {
        if (billStatus == null) {
            return billRepository.getBills(customerId);
        }
        return billRepository.getBills(customerId, billStatus);
    }

    @Override
    public CommonResponseDto saveOrder(int customerId, BillRequestDto requestDto) {
        //Validate
        Cart cart = cartService.getCartByCustomerId(customerId);
        List<Integer> listProductIds = requestDto.getListProductId();
        List<CartDetail> cartDetails = cart.getCartDetails()
                .stream()
                .filter(cartDetail -> (listProductIds.contains(cartDetail.getProduct().getId()) && !cartDetail.getProduct().getDeleteFlag()))
                .collect(Collectors.toList());
        if (cartDetails.size() == 0) {
            throw new InvalidException(ErrorMessage.Cart.ERR_NOT_FOUND_PRODUCT_IDS, listProductIds.toString());
        }
        //Create a new Bill
        Bill newBill = createNewBill(customerId, requestDto);
        //Create new Bill details
        double totalPrice = 30000;
        List<BillDetail> billDetails = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            //Get the total price
            Product product = cartDetail.getProduct();
            totalPrice += calculatePrice(product.getPrice(), product.getDiscount(), cartDetail.getQuantity());
            //Add new bill detail to list
            BillDetail newBillDetail = createNewBillDetail(product, cartDetail.getQuantity(), newBill);
            billDetails.add(newBillDetail);
        }
        newBill.setTotalAmount(totalPrice);
        newBill.setBillDetails(billDetails);

        cartDetailRepository.deleteAllInBatch(cartDetails);
        billRepository.save(newBill);

        String message = messageSource.getMessage(SuccessMessage.Bill.SAVE_ORDER, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public double calculatePrice(double price, int discount, int quantity) {
        double discountedPrice = price * (1 - discount / 100.0);
        return discountedPrice * quantity;
    }

    @Override
    public CommonResponseDto cancelOrder(int customerId, int billId) {
        BillStatus billStatus = billRepository.getBillStatus(billId, customerId);

        if (billStatus == null) {
            throw new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId));
        }

        if (!billStatus.equals(BillStatus.CANCELLED)) {
            if (!billStatus.equals(BillStatus.WAIT_FOR_CONFIRMATION)) {
                throw new InvalidException(ErrorMessage.Bill.ERR_NOT_ALLOW_CANCEL);
            }
            billRepository.updateBillStatus(billId, BillStatus.CANCELLED);
        }

        String message = messageSource.getMessage(SuccessMessage.Bill.CANCEL_ORDER, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public int getCountBillByStatus(int customerId, BillStatus billStatus) {
        return billRepository.getCountBillByStatusAndCustomerId(customerId, billStatus);
    }

    @Override
    public Long getCountBills() {
        return billRepository.count();
    }

    @Override
    public Bill getBill(int billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId)));
    }

    @Override
    public PaginationResponseDto<Bill> getBillsForAdmin(PaginationFullRequestDto requestDto, BillStatus billStatus) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BILL);

        Page<Bill> page = billRepository.findAll(filterBills(billStatus, requestDto.getKeyword(), requestDto.getSearchBy()), pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.BILL, page);

        PaginationResponseDto<Bill> responseDto = new PaginationResponseDto<>();

        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public CommonResponseDto updateBillStatus(int billId, BillStatus newStatus) {
        BillStatus billStatus = billRepository.getBillStatus(billId);

        if (billStatus == null) {
            throw new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId));
        }

        if (!newStatus.equals(billStatus)) {
            billRepository.updateBillStatus(billId, newStatus);
        }

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public GetBillDetailResponseDto getBillDetail(int customerId, int billId) {
        Bill bill = billRepository.getBillByIdAndCustomerId(customerId, billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId)));
        return billMapper.toGetBillDetailResponseDto(bill);
    }

}
