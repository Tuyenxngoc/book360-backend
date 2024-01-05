package com.bookstore.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
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
