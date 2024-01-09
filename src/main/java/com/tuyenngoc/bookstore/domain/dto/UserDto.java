package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    private int customerId;

    @Pattern(regexp = "^[a-z][a-z0-9]{3,15}$", message = ErrorMessage.INVALID_FORMAT_USERNAME)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;

    private String roleName;

}

