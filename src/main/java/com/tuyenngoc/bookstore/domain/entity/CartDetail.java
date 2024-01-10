package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_detail")
public class CartDetail extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id")
    private Integer id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_CART_DETAIL_PRODUCT_ID"), referencedColumnName = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "FK_CART_DETAIL_CART_ID"), referencedColumnName = "cart_id")
    @JsonBackReference
    private Cart cart;
}
