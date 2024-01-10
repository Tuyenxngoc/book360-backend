package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Integer id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private double shippingFee;

    private double totalAmount;

    private String paymentMethod;

    private String orderStatus;

    private String note;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BillDetail> billDetails;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_BILL_CUSTOMER_ID"), referencedColumnName = "customer_id")
    @JsonBackReference
    private Customer customer;

}
