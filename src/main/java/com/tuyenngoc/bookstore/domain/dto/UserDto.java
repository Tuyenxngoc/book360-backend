package com.tuyenngoc.bookstore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;

    private String customerName;

    private String email;

    private String roleName;

    private String avatar;

    private String address;

    private String gender;

}

