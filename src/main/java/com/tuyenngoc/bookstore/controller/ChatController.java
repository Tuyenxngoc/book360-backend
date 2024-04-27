package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.request.ChatMessageRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "chat")
public class ChatController {

    private final ChatMessageService chatMessageService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequestDto chatMessage) {
        ChatMessageResponseDto response = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getSenderName(), "/queue/messages", response);
    }

    @Operation(summary = "API get messages by chat room id")
    @GetMapping(UrlConstant.Chat.GET_MESSAGES)
    public ResponseEntity<?> getMessagesByChatRoomId(
            @PathVariable int chatRoomId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(chatMessageService.getMessagesByChatRoomId(chatRoomId, userDetails.getCustomerId()));
    }

    @Operation(summary = "API get chat rooms")
    @GetMapping(UrlConstant.Chat.GET_CHAT_ROOMS)
    public ResponseEntity<?> getChatRooms(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(chatMessageService.getChatRoomsByCustomerId(userDetails.getCustomerId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get all user send message")
    @GetMapping(UrlConstant.Chat.GET_USERS)
    public ResponseEntity<?> getUsersWithMessagesSent() {
        return VsResponseUtil.success(chatMessageService.getUsersWithMessagesSent());
    }

}
