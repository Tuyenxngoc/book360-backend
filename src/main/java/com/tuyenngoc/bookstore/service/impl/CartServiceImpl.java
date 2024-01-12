package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.repository.CartRepository;
import com.tuyenngoc.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public int getTotalProducts(Integer customerId) {
        Integer totalProducts = cartRepository.getTotalProductQuantityByCustomerId(customerId);
        if (totalProducts == null) {
            return 0;
        }
        return totalProducts;
    }
}
