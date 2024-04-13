package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;

import java.util.List;

public interface ChatMessageService {

    ChatMessageResponseDto save(ChatMessageRequestDto chatMessage);

    List<ChatMessageResponseDto> getMessagesBySenderId(int customerId);

    List<Customer> getUsersWithMessagesSent();

}
