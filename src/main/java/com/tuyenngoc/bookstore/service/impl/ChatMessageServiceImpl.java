package com.tuyenngoc.bookstore.service.impl;


import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.entity.ChatMessage;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.mapper.ChatMessageMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.ChatMessageRepository;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final CustomerRepository customerRepository;

    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatMessageResponseDto save(ChatMessageRequestDto requestDto) {
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(requestDto);
        Customer customer = customerRepository.findByUserName(requestDto.getSenderName())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        chatMessage.setCustomer(customer);

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDto(requestDto.getContent(), requestDto.getSenderName());
    }

    @Override
    public List<ChatMessageResponseDto> getMessagesBySenderId(int customerId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByCustomerId(customerId);

        List<ChatMessageResponseDto> responseDtos = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageResponseDto responseDto = new ChatMessageResponseDto();
            responseDto.setContent(chatMessage.getContent());
            responseDto.setSenderName(chatMessage.getCustomer().getUser().getUsername());
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    @Override
    public List<Customer> getUsersWithMessagesSent() {
        // Truy vấn cơ sở dữ liệu để lấy danh sách người dùng đã gửi tin nhắn
        List<Integer> userIds = chatMessageRepository.findDistinctSenderIds();
        // Lọc bớt những id trùng lặp, nếu có
        List<Integer> distinctUserIds = userIds.stream().distinct().collect(Collectors.toList());
        // Lấy thông tin chi tiết của từng người dùng
        return customerRepository.findByIdIn(distinctUserIds);
    }

}
