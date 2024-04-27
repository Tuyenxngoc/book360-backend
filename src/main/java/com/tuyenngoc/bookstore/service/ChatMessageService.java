package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatRoomResponseDto;

import java.util.List;

public interface ChatMessageService {

    ChatMessageResponseDto save(ChatMessageRequestDto chatMessage);

    List<ChatMessageResponseDto> getMessagesByChatRoomId(int chatRoomId, int customerId);

    List<ChatRoomResponseDto> getChatRoomsByCustomerId(int customerId);

    String getSupportUser();
}
