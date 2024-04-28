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

    private String chatRoomName;

    private String senderName;

    private String recipientName;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getId();
        this.chatRoomName = chatRoom.getName();
        this.senderName = chatRoom.getSender().getUser().getUsername();
        this.recipientName = chatRoom.getRecipient().getUser().getUsername();
    }
}
