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
import com.tuyenngoc.bookstore.domain.dto.response.bill.GetBillDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.bill.GetBillResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.statistics.SalesStatistics;
import com.tuyenngoc.bookstore.domain.dto.response.statistics.TimeSeriesData;
import com.tuyenngoc.bookstore.domain.entity.*;
import com.tuyenngoc.bookstore.domain.mapper.BillMapper;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.*;
import com.tuyenngoc.bookstore.service.BillService;
import com.tuyenngoc.bookstore.service.CartService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private final BillDetailRepository billDetailRepository;

    private final AddressDetailRepository addressDetailRepository;

    private final ProductRepository productRepository;

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
        // get address detail
        AddressDetail addressDetail = addressDetailRepository.findByCustomerIdAndId(customerId, requestDto.getAddressDetailId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(requestDto.getAddressDetailId())));
        // get cart
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
        newBill.setTotalPrice(totalPrice);
        newBill.setBillDetails(billDetails);
        // set address info
        newBill.setShippingName(addressDetail.getFullName());
        newBill.setShippingPhone(addressDetail.getPhoneNumber());
        newBill.setShippingAddress(addressDetail.getAddress().getFullAddress());
        // delete cart detail
        cartDetailRepository.deleteAllInBatch(cartDetails);
        // save new bill
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
    public CommonResponseDto cancelOrder(int customerId, int billId, String cancellationReason) {
        Bill bill = billRepository.getBillByCustomerIdAndId(billId, customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId)));

        BillStatus billStatus = bill.getBillStatus();
        if (!billStatus.equals(BillStatus.CANCELLED)) {
            if (!billStatus.equals(BillStatus.WAIT_FOR_CONFIRMATION)) {
                throw new InvalidException(ErrorMessage.Bill.ERR_NOT_ALLOW_CANCEL);
            }
            billRepository.cancelBill(billId, customerId, cancellationReason);
            for (BillDetail billDetail : bill.getBillDetails()) {
                Product product = billDetail.getProduct();
                int quantity = billDetail.getQuantity();
                product.setStockQuantity(product.getStockQuantity() + quantity);
                productRepository.save(product);
            }
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
        Bill bill = billRepository.getBillByCustomerIdAndId(customerId, billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, String.valueOf(billId)));
        return billMapper.toGetBillDetailResponseDto(bill);
    }

    @Override
    public SalesStatistics getKeyMetrics(String orderType) {
        // Get the current date
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime currentTime = LocalDateTime.now();

        // Get yesterday's date
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startOfYesterday = LocalDateTime.of(yesterday, LocalTime.MIN);
        LocalDateTime endOfYesterday = LocalDateTime.of(yesterday, LocalTime.MAX);

        List<TimeSeriesData> timeSeries = new ArrayList<>();

        LocalDateTime currentHour = startTime;
        while (currentHour.isBefore(currentTime)) {
            LocalDateTime nextHour = currentHour.plusHours(1);
            int billCountForHour = billRepository.getCountBillBetween(currentHour, nextHour);
            timeSeries.add(new TimeSeriesData(currentHour.getHour(), billCountForHour));
            currentHour = nextHour;
        }

        double sales = billRepository.getTotalRevenueBetween(startTime, currentTime);
        int productsSold = billDetailRepository.getCountProductSoldBetween(startTime, currentTime);
        int billCount = billRepository.getCountBillBetween(startTime, currentTime);

        double salesDiff = billRepository.getTotalRevenueBetween(startOfYesterday, endOfYesterday);
        int productsSoldDiff = billDetailRepository.getCountProductSoldBetween(startOfYesterday, endOfYesterday);
        int billCountDiff = billRepository.getCountBillBetween(startOfYesterday, endOfYesterday);

        double salesPctDiff = calculatePercentageDifference(sales, salesDiff);
        double productsPctDiff = calculatePercentageDifference(productsSold, productsSoldDiff);
        double ordersPctDiff = calculatePercentageDifference(billCount, billCountDiff);

        SalesStatistics salesStatistics = new SalesStatistics();
        salesStatistics.setSales(sales);
        salesStatistics.setProducts(productsSold);
        salesStatistics.setOrders(billCount);

        salesStatistics.setSalesPctDiff(salesPctDiff);
        salesStatistics.setProductsPctDiff(productsPctDiff);
        salesStatistics.setOrdersPctDiff(ordersPctDiff);

        salesStatistics.setTimeSeries(timeSeries);

        return salesStatistics;
    }

    private double calculatePercentageDifference(double todayCount, double yesterdayCount) {
        if (yesterdayCount != 0) {
            return ((todayCount - yesterdayCount) / yesterdayCount) * 100;
        } else {
            return 0.0;
        }
    }
}
