package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    private String fullName;

    private String biography;

    private String nationality;

    private String websiteUrl;

    private String socialMediaLinks;

    private String email;

    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;

    @Temporal(TemporalType.DATE)
    private LocalDate deathdate;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
