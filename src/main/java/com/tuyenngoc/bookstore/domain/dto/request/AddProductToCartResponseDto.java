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
public class AddProductToCartResponseDto {

    @Min(value = 0, message = ErrorMessage.MINIMUM_ZERO)
    private int productId;

    @Min(value = 1, message = ErrorMessage.MINIMUM_ONE)
    @Max(value = 100, message = ErrorMessage.MAXIMUM_ONE_HUNDRED)
    private int quantity;

}
