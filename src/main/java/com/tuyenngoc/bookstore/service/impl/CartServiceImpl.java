package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.CartDetailDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ProductFromCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CartDetailRepository;
import com.tuyenngoc.bookstore.repository.CartRepository;
import com.tuyenngoc.bookstore.service.CartService;
import com.tuyenngoc.bookstore.service.CustomerService;
import com.tuyenngoc.bookstore.service.ProductService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public CommonResponseDto addProductToCart(int customerId, CartDetailDto responseDto) {
        Product product = productService.getProduct(responseDto.getProductId());

        if (responseDto.getQuantity() > product.getStockQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewCart(customerId));

        CartDetail cartDetail = cart.getCartDetails()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> createNewCartDetail(cart, product));

        cartDetail.setQuantity(cartDetail.getQuantity() + responseDto.getQuantity());
        cartDetailRepository.save(cartDetail);

        String message = messageSource.getMessage(SuccessMessage.Cart.ADD_PRODUCT, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public List<ProductFromCartResponseDto> getProductsFromCart(int customerId) {
        return cartRepository.getProductFromCart(customerId);
    }

    @Override
    public CommonResponseDto updateCartDetail(int customerId, CartDetailDto cartDetailDto) {
        int cartId = cartRepository.getCartIdByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_CUSTOMER_ID, String.valueOf(customerId)));

        cartDetailRepository.updateCartDetail(cartId, cartDetailDto.getProductId(), cartDetailDto.getQuantity());

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto deleteProductFromCart(int customerId, int productId) {
        int cartId = cartRepository.getCartIdByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_CUSTOMER_ID, String.valueOf(customerId)));

        cartDetailRepository.deleteCartDetail(cartId, productId);

        String message = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }
}
