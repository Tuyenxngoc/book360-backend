package com.tuyenngoc.bookstore.domain.dto.request;


import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateCustomerRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String fullName;

    @NotNull(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private Gender gender;

    @NotNull(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private LocalDate dob;

}
