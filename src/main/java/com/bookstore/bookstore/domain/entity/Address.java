package com.bookstore.bookstore.domain.entity;

import com.bookstore.bookstore.domain.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    private double latitude;

    private double longitude;

    @Column(name = "address_name")
    private String addressName;

    @ManyToMany(mappedBy = "addresses")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();
}
