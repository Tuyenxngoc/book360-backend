package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Size(min = -90, max = 90, message = ErrorMessage.INVALID_COORDINATES)
    private double latitude;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Size(min = -180, max = 180, message = ErrorMessage.INVALID_COORDINATES)
    private double longitude;

    private String addressName;

    public AddressDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
