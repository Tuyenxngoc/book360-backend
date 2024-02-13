package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.request.CreateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.UpdateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetTodoResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.Role;
import com.tuyenngoc.bookstore.domain.entity.User;
import com.tuyenngoc.bookstore.domain.mapper.CustomerMapper;
import com.tuyenngoc.bookstore.domain.mapper.UserMapper;
import com.tuyenngoc.bookstore.exception.DataIntegrityViolationException;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.BillRepository;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.repository.UserRepository;
import com.tuyenngoc.bookstore.service.*;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ProductService productService;

    private final BillRepository billRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final UploadFileUtil uploadFileUtil;

    private final MessageSource messageSource;

    private final UploadRedisService uploadRedisService;

    private final AddressService addressService;

    private final RoleService roleService;

    private final CustomerMapper customerMapper;

    private final UserMapper userMapper;

    private final CartService cartService;

    @Value("${upload.file.size.limit}")
    private String fileSizeLimit;

    @Override
    public PaginationResponseDto<GetProductResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.PRODUCT);

        Page<GetProductResponseDto> page = customerRepository.getFavoriteProducts(customerId, pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductResponseDto> responseDto = new PaginationResponseDto<>();
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
        Customer customer = getCustomer(customerId);
        Product product = productService.getProduct(productId);

        customer.getFavoriteProducts().add(product);

        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto removeFavoriteProduct(int customerId, int productId) {
        Customer customer = getCustomer(customerId);
        Product product = productService.getProduct(productId);

        customer.getFavoriteProducts().remove(product);

        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto uploadAvatar(int customerId, MultipartFile file) {
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

        Customer customer = getCustomer(customerId);
        uploadFileUtil.destroyFileWithUrl(customer.getAvatar());

        String newUrl = uploadFileUtil.uploadFile(file);
        customer.setAvatar(newUrl);
        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
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
        //Save image urls to redis cache
        uploadRedisService.saveUrls(username, uploadedFiles);
        return uploadedFiles;
    }

    @Override
    public long parseSize(String size) {
        try {
            size = size.toUpperCase();
            long parseLong = Long.parseLong(size.substring(0, size.length() - 2));
            if (size.endsWith("KB")) {
                return parseLong * 1024;
            } else if (size.endsWith("MB")) {
                return parseLong * 1024 * 1024;
            } else if (size.endsWith("GB")) {
                return parseLong * 1024 * 1024 * 1024;
            } else {
                return parseLong;
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return 2 * 1024 * 1024;
        }
    }

    @Override
    public CommonResponseDto updateCustomer(int customerId, UpdateCustomerRequestDto updateCustomerRequestDto) {
        Customer customer = getCustomer(customerId);

        customer.setFullName(updateCustomerRequestDto.getFullName());
        customer.setGender(updateCustomerRequestDto.getGender());
        customer.setDob(updateCustomerRequestDto.getDob());

        customerRepository.save(customer);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public GetTodoResponseDto getTodo(int customerId) {
        int productSoldOut = productRepository.getCountProductSoldOut();
        int waitForConfirmationCount = billRepository.getCountBillByStatus(BillStatus.WAIT_FOR_CONFIRMATION);
        int waitForDeliveryCount = billRepository.getCountBillByStatus(BillStatus.WAIT_FOR_DELIVERY);
        int deliveringCount = billRepository.getCountBillByStatus(BillStatus.DELIVERING);
        int cancelledCount = billRepository.getCountBillByStatus(BillStatus.CANCELLED);
        return new GetTodoResponseDto(productSoldOut, waitForConfirmationCount, waitForDeliveryCount, deliveringCount, cancelledCount);
    }

    @Override
    public long getCountCustomer() {
        return customerRepository.count();
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

    @Override
    public Customer getCustomer(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));
    }

    @Override
    public Customer createCustomer(CreateCustomerRequestDto requestDto) {
        boolean isUsernameExists = userRepository.existsByUsername(requestDto.getUsername());
        if (isUsernameExists) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_USERNAME);
        }
        boolean isEmailExists = userRepository.existsByEmail(requestDto.getEmail());
        if (isEmailExists) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_EMAIL);
        }
        Role role = roleService.getRole(requestDto.getRoleName());
        //Create Address todo
//        Address address = addressService.createAddress(requestDto.getAddressName());

        //Create Customer
        Customer customer = customerMapper.toCustomer(requestDto);
//        customer.setAddress(address);
        customerRepository.save(customer);
        //Create User
        User newUser = userMapper.toUser(requestDto);
        newUser.setRole(role);
        newUser.setCustomer(customer);
        userRepository.save(newUser);
        //Create Cart
        cartService.createNewCart(customer);
        return customer;
    }
}
