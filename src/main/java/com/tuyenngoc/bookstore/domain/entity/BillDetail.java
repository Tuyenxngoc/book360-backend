package com.tuyenngoc.bookstore.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill_detail")
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id")
    private Integer id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_BILL_DETAIL_PRODUCT_ID"), referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "bill_id", foreignKey = @ForeignKey(name = "FK_BILL_DETAIL_BILL_ID"), referencedColumnName = "bill_id")
    private Bill bill;
}
