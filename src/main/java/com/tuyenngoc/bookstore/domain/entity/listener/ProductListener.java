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
        log.info("PrePersist");
    }

    @PreUpdate
    public void preUpdate(Product product) {
        log.info("PreUpdate");
    }

    @PreRemove
    public void preRemove(Product product) {
        log.info("PreRemove");
    }

    @PostLoad
    public void postLoad(Product product) {
        log.info("PostLoad");
    }

    @PostRemove
    public void postRemove(Product product) {
        log.info("PostRemove");
    }

    @PostUpdate
    public void postUpdate(Product product) {
        log.info("PostUpdate");
    }

    @PostPersist
    public void postPersist(Product product) {
        log.info("PostPersist");
    }

}
