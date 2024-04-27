package com.tuyenngoc.bookstore.repository;

import com.tuyenngoc.bookstore.domain.dto.response.ChatRoomResponseDto;
import com.tuyenngoc.bookstore.domain.entity.ChatRoom;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    Optional<ChatRoom> findBySenderAndRecipient(Customer sender, Customer recipient);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.sender.id = :senderId")
    List<ChatRoom> findBySenderId(@Param("senderId") int senderId);

    @Query("SELECT CASE WHEN COUNT(cr.id) > 0 THEN true ELSE false END " +
            "FROM ChatRoom cr WHERE " +
            "(cr.sender.id = :customerId OR cr.recipient.id = :customerId) AND " +
            "cr.id = :chatRoomId")
    boolean existsByChatRoomIdAndCustomerId(
            @Param("chatRoomId") int chatRoomId,
            @Param("customerId") int customerId
    );

    @Query("SELECT new com.tuyenngoc.bookstore.domain.dto.response.ChatRoomResponseDto(cr) FROM " +
            "ChatRoom cr WHERE " +
            "cr.sender.id = :customerId OR " +
            "cr.recipient.id = :customerId")
    List<ChatRoomResponseDto> findAllByCustomerId(@Param("customerId") int customerId);
}
