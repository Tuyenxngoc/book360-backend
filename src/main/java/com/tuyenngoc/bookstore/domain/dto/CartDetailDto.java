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
public class CartDetailDto {

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer productId;

    @Min(value = 1, message = ErrorMessage.MINIMUM_ONE)
    @Max(value = 100, message = ErrorMessage.MAXIMUM_ONE_HUNDRED)
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer quantity;
}
