package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleConstant name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users = new ArrayList<>();
}
