package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.CartDetailDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductFromCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CartDetailRepository;
import com.tuyenngoc.bookstore.repository.CartRepository;
import com.tuyenngoc.bookstore.service.CartService;
import com.tuyenngoc.bookstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    private final ProductService productService;

    private final MessageSource messageSource;

    @Override
    public Cart createNewCart(int customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return createNewCart(customer);
    }

    @Override
    public Cart createNewCart(Customer customer) {
        Cart newCart = new Cart();
        newCart.setCustomer(customer);
        return cartRepository.save(newCart);
    }

    @Override
    public CartDetail createNewCartDetail(Cart cart, Product product) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setQuantity(0);
        return cartDetail;
    }

    @Override
    public Cart getCartByCustomerId(int customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_CUSTOMER_ID, String.valueOf(customerId)));
    }

    @Override
    public int getTotalProducts(int customerId) {
        return cartRepository.getTotalProductQuantityByCustomerId(customerId);
    }

    @Override
    public CommonResponseDto addProductToCart(int customerId, CartDetailDto requestDto) {
        Product product = productService.getProduct(requestDto.getProductId());

        if (requestDto.getQuantity() > product.getStockQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewCart(customerId));

        CartDetail cartDetail = cart.getCartDetails()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> createNewCartDetail(cart, product));

        int totalProduct = cartDetail.getQuantity() + requestDto.getQuantity();

        if (totalProduct > product.getStockQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        cartDetail.setQuantity(totalProduct);
        cartDetailRepository.save(cartDetail);

        String message = messageSource.getMessage(SuccessMessage.Cart.ADD_PRODUCT, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public List<GetProductFromCartResponseDto> getProductsFromCart(int customerId) {
        return cartRepository.getProductFromCart(customerId);
    }

    @Override
    public CommonResponseDto updateCartDetail(int customerId, CartDetailDto requestDto) {
        Product product = productService.getProduct(requestDto.getProductId());

        CartDetail cartDetail = cartDetailRepository.getCartDetail(customerId, requestDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_PRODUCT_ID, String.valueOf(requestDto.getProductId())));

        if (requestDto.getQuantity() > product.getStockQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        if (cartDetail.getQuantity() != requestDto.getQuantity()) {
            cartDetail.setQuantity(requestDto.getQuantity());
            cartDetailRepository.save(cartDetail);
        }

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto deleteProductFromCart(int customerId, int productId) {
        cartDetailRepository.deleteCartDetail(customerId, productId);
        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }
}
