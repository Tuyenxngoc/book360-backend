package com.bookstore.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;

    @Column(nullable = false)
    private String image;

    private String image_500x500;

    private String image_50x50;

    @ManyToOne
    @JoinColumn(name = "images")
    @JsonBackReference
    private Product product;
}
