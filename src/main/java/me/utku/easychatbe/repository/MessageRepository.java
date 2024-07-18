package me.utku.easychatbe.repository;

import me.utku.easychatbe.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findAllByReceiver_IdOrderByCreatedAtDesc(UUID receiverId, Pageable pageable);
}
