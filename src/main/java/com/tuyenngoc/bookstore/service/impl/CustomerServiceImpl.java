package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.request.UpdateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetTodoResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.BillRepository;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.service.CustomerService;
import com.tuyenngoc.bookstore.service.UploadRedisService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import com.tuyenngoc.bookstore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final BillRepository billRepository;

    private final UploadFileUtil uploadFileUtil;

    private final MessageSource messageSource;

    private final UploadRedisService uploadRedisService;

    @Value("${upload.file.size.limit}")
    private String fileSizeLimit;

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.PRODUCT);

        Page<GetProductsResponseDto> page = customerRepository.getFavoriteProducts(customerId, pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public boolean checkFavoriteProduct(int customerId, int productId) {
        return customerRepository.isProductFavoriteForCustomer(customerId, productId);
    }

    @Override
    public CommonResponseDto addFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
        customer.getFavoriteProducts().add(product);
        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto removeFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
        customer.getFavoriteProducts().remove(product);
        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public String uploadImage(String username, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidException(ErrorMessage.INVALID_FILE_REQUIRED);
        }

        if (file.getSize() > parseSize(fileSizeLimit)) {
            throw new InvalidException(ErrorMessage.INVALID_FILE_SIZE, fileSizeLimit);
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidException(ErrorMessage.INVALID_FILE_TYPE);
        }

        String newUrl = uploadFileUtil.uploadFile(file);
        uploadRedisService.saveUrls(username, List.of(newUrl));

        return newUrl;
    }

    @Override
    public List<String> uploadImages(String username, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new InvalidException(ErrorMessage.INVALID_FILE_REQUIRED);
        }

        List<String> uploadedFiles = new ArrayList<>();
        long maxSizeBytes = parseSize(fileSizeLimit);
        for (MultipartFile file : files) {
            if (file.getSize() > maxSizeBytes) {
                throw new InvalidException(ErrorMessage.INVALID_FILE_SIZE, fileSizeLimit);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new InvalidException(ErrorMessage.INVALID_FILE_TYPE);
            }

            String newUrl = uploadFileUtil.uploadFile(file);
            uploadedFiles.add(newUrl);
        }
        uploadRedisService.saveUrls(username, uploadedFiles);
        return uploadedFiles;
    }

    private long parseSize(String size) {
        size = size.toUpperCase();
        if (size.endsWith("KB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024;
        } else if (size.endsWith("MB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024 * 1024;
        } else if (size.endsWith("GB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024 * 1024 * 1024;
        } else {
            return Long.parseLong(size);
        }
    }

    @Override
    public CommonResponseDto updateCustomer(int customerId, UpdateCustomerRequestDto updateCustomerRequestDto) {
        LocalDate dob;
        try {
            dob = LocalDate.parse(updateCustomerRequestDto.getDob());
            if (dob.isAfter(LocalDate.now())) {
                throw new InvalidException(ErrorMessage.INVALID_DATE_PAST);
            }
        } catch (DateTimeParseException e) {
            throw new InvalidException(ErrorMessage.INVALID_DATE, updateCustomerRequestDto.getDob());
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));

        Address address = new Address();
        address.setAddressName(updateCustomerRequestDto.getAddress());

        customer.setFullName(updateCustomerRequestDto.getFullName());
        customer.setPhoneNumber(updateCustomerRequestDto.getPhoneNumber());
        customer.setAvatar(updateCustomerRequestDto.getAvatar());
        customer.setDob(dob);
        customer.setAddress(address);
        customer.setGender(updateCustomerRequestDto.getGender());

        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public GetTodoResponseDto getTodo(int customerId) {
        int productSoldOut = productRepository.getCountProductSoldOut();
        int waitForConfirmationCount = billRepository.getCountBillByStatus(BillStatus.WAIT_FOR_CONFIRMATION.getName());
        int waitForDeliveryCount = billRepository.getCountBillByStatus(BillStatus.WAIT_FOR_DELIVERY.getName());
        int deliveringCount = billRepository.getCountBillByStatus(BillStatus.DELIVERING.getName());
        int cancelledCount = billRepository.getCountBillByStatus(BillStatus.CANCELLED.getName());
        return new GetTodoResponseDto(productSoldOut, waitForConfirmationCount, waitForDeliveryCount, deliveringCount, cancelledCount);
    }

    @Override
    public int getCountCustomer() {
        return customerRepository.getCountCustomer();
    }

    @Override
    public PaginationResponseDto<Customer> getCustomers(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.CUSTOMER);

        Page<Customer> page = customerRepository.getCustomersByFullName(requestDto.getKeyword(), pageable);
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.CUSTOMER, page);

        PaginationResponseDto<Customer> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(meta);

        return responseDto;
    }
}
