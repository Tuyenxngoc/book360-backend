package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
public class Author extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    private List<Product> products = new ArrayList<>();

}
