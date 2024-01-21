package com.tuyenngoc.bookstore.domain.dto.request;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String refreshToken;

}