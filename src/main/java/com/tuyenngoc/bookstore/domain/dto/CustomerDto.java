package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.Gender;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer id;

    private String fullName;

    private LocalDate dob;

    private String phoneNumber;

    private Gender gender;

    private String avatar;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.fullName = customer.getFullName();
        this.dob = customer.getDob();
        this.phoneNumber = customer.getPhoneNumber();
        this.gender = customer.getGender();
        this.avatar = customer.getAvatar();
    }
}
