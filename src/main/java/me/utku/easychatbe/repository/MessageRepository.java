package me.utku.easychatbe.repository;

import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    public Message findFirstByReceiverOrderByCreatedAtDesc(ChatRoom receiver);
}
