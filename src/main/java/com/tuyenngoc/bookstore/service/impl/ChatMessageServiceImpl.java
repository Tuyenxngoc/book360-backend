package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatRoomResponseDto;
import com.tuyenngoc.bookstore.domain.entity.ChatMessage;
import com.tuyenngoc.bookstore.domain.entity.ChatRoom;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.User;
import com.tuyenngoc.bookstore.domain.mapper.ChatMessageMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.ChatMessageRepository;
import com.tuyenngoc.bookstore.repository.ChatRoomRepository;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.repository.UserRepository;
import com.tuyenngoc.bookstore.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final CustomerRepository customerRepository;

    private final ChatMessageMapper chatMessageMapper;

    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    @Override
    public ChatMessageResponseDto save(ChatMessageRequestDto requestDto) {
        Customer sender = customerRepository.findByUserName(requestDto.getSenderName())
                .orElse(null);
        Customer recipient = customerRepository.findByUserName(requestDto.getRecipientName())
                .orElse(null);

        if (sender == null || recipient == null) {
            return null;
        }

        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(sender.getId(), recipient.getId())
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setName(sender.getUser().getUsername());
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

    @Override
    public String getSupportUser() {
        List<User> supportUsers = userRepository
                .findByRole_NameIn(Arrays.asList(RoleConstant.ADMINISTRATOR, RoleConstant.CUSTOMER_SUPPORT));

        if (!supportUsers.isEmpty()) {
            return supportUsers.get(0).getUsername();
        } else {
            return null;
        }
    }

}
