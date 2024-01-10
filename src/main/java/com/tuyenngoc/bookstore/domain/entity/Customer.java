package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuyenngoc.bookstore.constant.Gender;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Nationalized
    @Column(nullable = false)
    private String fullName;

    private LocalDate dob;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "customer")
    @JsonBackReference
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CART_ID"), referencedColumnName = "cart_id")
    @JsonManagedReference
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Bill> bills;

    @ManyToOne
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDRESS_ID"), referencedColumnName = "address_id")
    @JsonBackReference
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "customer_favorite_products",
            joinColumns = @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_FAVORITE_PRODUCT_CUSTOMER_ID"), referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_FAVORITE_PRODUCT_PRODUCT_ID"), referencedColumnName = "product_id")
    )
    @JsonManagedReference
    private List<Product> products;
}
