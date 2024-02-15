package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesRequestDto {

    @Max(value = 90, message = ErrorMessage.INVALID_COORDINATES)
    @Min(value = -90, message = ErrorMessage.INVALID_COORDINATES)
    private Double latitude;

    @Max(value = 180, message = ErrorMessage.INVALID_COORDINATES)
    @Min(value = -180, message = ErrorMessage.INVALID_COORDINATES)
    private Double longitude;

}
