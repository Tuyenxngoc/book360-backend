package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatRoomResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;

import java.util.List;

public interface ChatMessageService {

    ChatMessageResponseDto save(ChatMessageRequestDto chatMessage);

    List<Customer> getUsersWithMessagesSent();

    List<ChatMessageResponseDto> getMessagesByChatRoomId(int chatRoomId, int customerId);

    List<ChatRoomResponseDto> getChatRoomsByCustomerId(int customerId);
}
