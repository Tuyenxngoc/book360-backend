package com.tuyenngoc.bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE_ID"))
    @JsonManagedReference
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_USER_CUSTOMER_ID"))
    @JsonManagedReference
    private Customer customer;
}
