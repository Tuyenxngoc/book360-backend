package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuyenngoc.bookstore.constant.Gender;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Nationalized
    @Column(nullable = false)
    private String name;

    private LocalDate dob;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "customers_addresses",
            joinColumns = @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUST_ADDR_CUSTOMER_ID")),
            inverseJoinColumns = @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "FK_CUST_ADDR_ADDRESS_ID"))
    )
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "customer_favorite_products",
            joinColumns = @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUST_PROD_CUSTOMER_ID")),
            inverseJoinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_CUST_PROD_PRODUCT_ID"))
    )
    private Set<Product> favoriteProducts = new HashSet<>();

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Bill> bills = new ArrayList<>();

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dob = LocalDate.now();
    }
}
