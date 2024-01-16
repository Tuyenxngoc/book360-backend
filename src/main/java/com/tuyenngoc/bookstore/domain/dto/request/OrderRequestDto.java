package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String consigneeName;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?:\\+84|0)(?:1[2689]|9[0-9]|3[2-9]|5[6-9]|7[0-9])(?:\\d{7}|\\d{8})$")
    private String phoneNumber;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String shippingAddress;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String paymentMethod;

    private String note;

    @NotEmpty(message = ErrorMessage.NOT_BLANK_FIELD)
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private List<Integer> listProductId;

}
