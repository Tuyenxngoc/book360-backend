package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "address")
public class Address extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @Column(name = "address_name")
    private String addressName;

    private double latitude;

    private double longitude;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AddressDetail> addressDetail;

}
