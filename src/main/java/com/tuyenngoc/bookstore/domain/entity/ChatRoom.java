package com.tuyenngoc.bookstore.domain.entity;

import com.tuyenngoc.bookstore.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "FK_CHAT_ROOM_SENDER_ID"), referencedColumnName = "customer_id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", foreignKey = @ForeignKey(name = "FK_CHAT_ROOM_RECIPIENT_ID"), referencedColumnName = "customer_id")
    private Customer recipient;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();
}
