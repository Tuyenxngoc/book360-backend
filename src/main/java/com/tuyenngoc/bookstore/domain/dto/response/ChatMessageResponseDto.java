package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.domain.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {

    private String content;

    private String senderName;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.content = chatMessage.getContent();
        this.senderName = chatMessage.getCustomer().getUser().getUsername();
    }
}
