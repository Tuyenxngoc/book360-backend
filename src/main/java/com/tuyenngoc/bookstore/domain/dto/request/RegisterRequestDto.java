package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = "^[a-z][a-z0-9]{3,15}$", message = ErrorMessage.INVALID_FORMAT_USERNAME)
    private String username;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    private String password;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String repeatPassword;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;

}
