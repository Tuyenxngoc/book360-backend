package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Max(value = 90, message = ErrorMessage.INVALID_COORDINATES)
    @Min(value = -90, message = ErrorMessage.INVALID_COORDINATES)
    private Double latitude;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Max(value = 180, message = ErrorMessage.INVALID_COORDINATES)
    @Min(value = -180, message = ErrorMessage.INVALID_COORDINATES)
    private Double longitude;

    private String addressName;

}
