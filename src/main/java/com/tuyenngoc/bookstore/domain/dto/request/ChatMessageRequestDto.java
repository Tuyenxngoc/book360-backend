package com.tuyenngoc.bookstore.domain.dto.request;

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
public class ChatMessageRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    @Size(max = 120, message = ErrorMessage.INVALID_TEXT_LENGTH)
    private String content;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String senderName;

    @NotBlank(message = ErrorMessage.INVALID_NOT_BLANK_FIELD)
    private String recipientName;

}
