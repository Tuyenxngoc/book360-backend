package com.tuyenngoc.bookstore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;

    private String nickName;

    private String roleName;

    private String email;

    private String phoneNumber;

    private String address;

    private String avatar;

    private String gender;

    private LocalDate dob;

}

