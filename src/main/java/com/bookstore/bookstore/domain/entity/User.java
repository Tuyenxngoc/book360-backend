package com.bookstore.bookstore.domain.entity;

import com.bookstore.bookstore.domain.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateAuditing {

    @Id
    @UuidGenerator
    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"), referencedColumnName = "role_id")
    @JsonBackReference
    private Role role;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true, foreignKey = @ForeignKey(name = "FK_USER_CUSTOMER"), referencedColumnName = "customer_id")
    private Customer customer;
}
