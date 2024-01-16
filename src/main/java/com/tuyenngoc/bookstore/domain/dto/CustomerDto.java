package com.tuyenngoc.bookstore.domain.dto;


import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String fullName;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?:\\+84|0)(?:1[2689]|9[0-9]|3[2-9]|5[6-9]|7[0-9])(?:\\d{7}|\\d{8})$", message = ErrorMessage.INVALID_FORMAT_PHONE)
    private String phoneNumber;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String address;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String avatar;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private LocalDate dob;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private Gender gender;
}
