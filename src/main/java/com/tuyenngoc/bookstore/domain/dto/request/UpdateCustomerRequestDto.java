package com.tuyenngoc.bookstore.domain.dto.request;


import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateCustomerRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String fullName;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?:\\+84|0)(?:1[2689]|9[0-9]|3[2-9]|5[6-9]|7[0-9])(?:\\d{7}|\\d{8})$", message = ErrorMessage.INVALID_FORMAT_PHONE)
    private String phoneNumber;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String address;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String avatar;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?:19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = ErrorMessage.INVALID_LOCAL_DATE_FORMAT)
    private String dob;

    @NotNull(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private Gender gender;

}
