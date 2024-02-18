package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.AddressType;
import com.tuyenngoc.bookstore.constant.CommonConstant;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequestDto {

    private Integer addressId;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Size(min = 2, max = 255, message = ErrorMessage.INVALID_TEXT_LENGTH)
    @Pattern(regexp = CommonConstant.REGEXP_FULL_NAME, message = ErrorMessage.INVALID_FORMAT_SOME_THING_FIELD)
    private String fullName;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = CommonConstant.REGEXP_PHONE_NUMBER, message = ErrorMessage.INVALID_FORMAT_PHONE)
    private String phoneNumber;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String state;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String district;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String ward;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String street;

    @Max(value = 90, message = ErrorMessage.INVALID_COORDINATES)
    @Min(value = -90, message = ErrorMessage.INVALID_COORDINATES)
    private Double latitude;

    @Max(value = 180, message = ErrorMessage.INVALID_COORDINATES)
    @Min(value = -180, message = ErrorMessage.INVALID_COORDINATES)
    private Double longitude;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Boolean isDefaultAddress;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private AddressType type;

}
