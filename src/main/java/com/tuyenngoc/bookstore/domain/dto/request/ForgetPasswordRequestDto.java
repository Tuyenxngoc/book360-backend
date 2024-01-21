package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String username;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String email;

}
