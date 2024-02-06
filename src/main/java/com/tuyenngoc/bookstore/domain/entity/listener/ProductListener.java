package com.tuyenngoc.bookstore.domain.entity.listener;

import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.service.ProductRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ProductListener {

    private final ProductRedisService productRedisService;

    @PrePersist
    public void prePersist(Product product) {
    }

    @PreUpdate
    public void preUpdate(Product product) {
    }

    @PreRemove
    public void preRemove(Product product) {
    }

    @PostLoad
    public void postLoad(Product product) {
    }

    @PostRemove
    public void postRemove(Product product) {
    }

    @PostUpdate
    public void postUpdate(Product product) {
        productRedisService.clearAllProductCache();
        productRedisService.clearProductDetailCache(product.getId());
        productRedisService.clearProductSameAuthorCache(product.getId());
    }

    @PostPersist
    public void postPersist(Product product) {
        productRedisService.clearAllProductCache();
    }

}
