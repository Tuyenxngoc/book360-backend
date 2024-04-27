package com.tuyenngoc.bookstore.service.impl;


import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatRoomResponseDto;
import com.tuyenngoc.bookstore.domain.entity.ChatMessage;
import com.tuyenngoc.bookstore.domain.entity.ChatRoom;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.mapper.ChatMessageMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.ChatMessageRepository;
import com.tuyenngoc.bookstore.repository.ChatRoomRepository;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final CustomerRepository customerRepository;

    private final ChatMessageMapper chatMessageMapper;

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatMessageResponseDto save(ChatMessageRequestDto requestDto) {
        Customer sender = customerRepository.findByUserName(requestDto.getSenderName())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_NAME, requestDto.getSenderName()));

        Customer recipient = customerRepository.findByUserName(requestDto.getRecipientName())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_NAME, requestDto.getRecipientName()));

        ChatRoom chatRoom = chatRoomRepository.findBySenderAndRecipient(sender, recipient)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setSender(sender);
                    newChatRoom.setRecipient(recipient);
                    return chatRoomRepository.save(newChatRoom);
                });

        ChatMessage chatMessage = chatMessageMapper.toChatMessage(requestDto);
        chatMessage.setCustomer(sender);
        chatMessage.setChatRoom(chatRoom);
        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDto(requestDto.getContent(), requestDto.getSenderName());
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

    @Override
    public List<ChatMessageResponseDto> getMessagesByChatRoomId(int chatRoomId, int customerId) {
        if (!chatRoomRepository.existsByChatRoomIdAndCustomerId(chatRoomId, customerId)) {
            throw new NotFoundException(ErrorMessage.ChatRoom.ERR_NOT_FOUND_ID, String.valueOf(chatRoomId));
        }
        return chatMessageRepository.findAllByChatRoomId(chatRoomId);
    }

    @Override
    public List<ChatRoomResponseDto> getChatRoomsByCustomerId(int customerId) {
        return chatRoomRepository.findAllByCustomerId(customerId);
    }

}
