package me.utku.easychatbe.service;

import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService implements BaseService<ChatRoom> {
    private final ChatRoomRepository chatRoomRepository;
    private final AuthService authService;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, AuthService authService) {
        this.chatRoomRepository = chatRoomRepository;
        this.authService = authService;
    }

    @Override
    public ChatRoom getEntityById(UUID id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new RuntimeException("Entity not found");
        return chatRoom;
    }

    @Override
    public List<ChatRoom> getAllEntities() {
        return chatRoomRepository.findAll();
    }

    @Override
    public ChatRoom createEntity(ChatRoom entity) {
        User authUser = authService.getAuthenticatedUser();
        entity.setCreatedBy(authUser);
        entity.setMembers(List.of(authUser));
        return chatRoomRepository.save(entity);
    }

    @Override
    public ChatRoom updateEntity(UUID id, ChatRoom newEntity) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        return chatRoomRepository.save(newEntity);
    }

    @Override
    public void deleteEntity(UUID id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        chatRoomRepository.deleteById(id);
    }
}
