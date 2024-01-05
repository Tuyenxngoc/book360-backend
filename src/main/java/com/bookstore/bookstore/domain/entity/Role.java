package com.bookstore.bookstore.domain.entity;

import com.bookstore.bookstore.domain.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Role extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<User> users = new HashSet<>();
}
