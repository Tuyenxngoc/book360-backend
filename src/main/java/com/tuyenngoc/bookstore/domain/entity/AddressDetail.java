package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuyenngoc.bookstore.constant.AddressType;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address_detail")
public class AddressDetail extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_detail_id")
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private AddressType type;

    @Column(name = "is_default_address")
    private boolean isDefaultAddress;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "FK_ADDRESS_DETAIL_ADDRESS_ID"), referencedColumnName = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_ADDRESS_DETAIL_CUSTOMER_ID"), referencedColumnName = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "shippingAddress", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Bill> bills;
}
