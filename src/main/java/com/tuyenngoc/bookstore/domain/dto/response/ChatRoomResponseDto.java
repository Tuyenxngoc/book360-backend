package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.domain.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto {

    private int chatRoomId;

    private int senderId;

    private int recipientId;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getId();
        this.senderId = chatRoom.getSender().getId();
        this.recipientId = chatRoom.getRecipient().getId();
    }
}
