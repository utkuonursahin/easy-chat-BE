package me.utku.easychatbe.chatroom;

import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    List<ChatRoom> findAllByMembersContaining(User user);
}
