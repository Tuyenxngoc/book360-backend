package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.request.AddProductToCartResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ProductFromCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.mapper.CartDetailMapper;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CartRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartDetailMapper cartDetailMapper;

    private final MessageSource messageSource;

    @Override
    public int getTotalProducts(Integer customerId) {
        return cartRepository.getTotalProductQuantityByCustomerId(customerId);
    }

    @Override
    public CommonResponseDto addProductToCart(AddProductToCartResponseDto responseDto, int customerId) {

        Product product = productRepository.findById(responseDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(responseDto.getProductId())));

        if (responseDto.getQuantity() > product.getStockQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_CUSTOMER_ID, String.valueOf(customerId)));

        List<CartDetail> cartDetails = cart.getCartDetails();

        Optional<CartDetail> cartDetailOptional = cartDetails.stream()
                .filter(cartDetail -> cartDetail.getProduct().getId().equals(product.getId()))
                .findFirst();

        CartDetail cartDetail;
        if (cartDetailOptional.isPresent()) {
            cartDetail = cartDetailOptional.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + responseDto.getQuantity());
        } else {
            cartDetail = cartDetailMapper.toCartDetail(responseDto);
            cartDetail.setProduct(product);
        }
        cartDetail.setCart(cart);
        cartDetails.add(cartDetail);
        cartRepository.save(cart);

        String message = messageSource.getMessage(SuccessMessage.Cart.ADD_PRODUCT_TO_CAR, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public List<ProductFromCartResponseDto> getProductsFromCart(int customerId) {
        return cartRepository.getProductFromCart(customerId);
    }
}
