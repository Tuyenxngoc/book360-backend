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
import java.util.ArrayList;
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
    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Temporal(TemporalType.DATE)
    private LocalDate dob;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String avatar;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonBackReference
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Bill> bills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDRESS_ID"), referencedColumnName = "address_id")
    @JsonBackReference
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_favorite_products",
            joinColumns = @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_FAVORITE_PRODUCT_CUSTOMER_ID"), referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_FAVORITE_PRODUCT_PRODUCT_ID"), referencedColumnName = "product_id")
    )
    @JsonManagedReference
    private List<Product> favoriteProducts = new ArrayList<>();

    public Customer(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

}
