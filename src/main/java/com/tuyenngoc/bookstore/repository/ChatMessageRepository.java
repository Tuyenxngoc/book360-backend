package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto;
import com.tuyenngoc.bookstore.domain.entity.ChatMessage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    List<ChatMessage> findAllByCustomerId(int customerId);

    @Query("SELECT DISTINCT c.customer.id FROM ChatMessage c")
    List<Integer> findDistinctSenderIds();

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.ChatMessageResponseDto(c) " +
            "FROM ChatMessage c WHERE " +
            "c.chatRoom.id =:chatRoomId")
    List<ChatMessageResponseDto> findAllByChatRoomId(@Param("chatRoomId") int chatRoomId);
}
