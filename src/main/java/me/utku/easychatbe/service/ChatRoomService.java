package me.utku.easychatbe.service;

import me.utku.easychatbe.dto.ChatRoomDto;
import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChatRoomService implements BaseService<ChatRoom,ChatRoomDto> {
    private final ChatRoomRepository chatRoomRepository;
    private final AuthService authService;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, AuthService authService) {
        this.chatRoomRepository = chatRoomRepository;
        this.authService = authService;
    }

    @Override
    public ChatRoomDto getEntityById(UUID id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new RuntimeException("Entity not found");
        return chatRoom.toChatRoomDto();
    }

    @Override
    public List<ChatRoomDto> getAllEntities() {
        return chatRoomRepository.findAll().stream().map(ChatRoom::toChatRoomDto).toList();
    }

    @Override
    public ChatRoomDto createEntity(ChatRoom entity) {
        User authUser = authService.getAuthenticatedUser();
        entity.setCreatedBy(authUser);
        entity.setMembers(List.of(authUser));
        return chatRoomRepository.save(entity).toChatRoomDto();
    }

    @Override
    public ChatRoomDto updateEntity(UUID id, ChatRoom updateEntity) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        chatRoom = chatRoomRepository.save(updateEntity);
        return chatRoom.toChatRoomDto();
    }

    @Override
    public void deleteEntity(UUID id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        chatRoomRepository.deleteById(id);
    }

    public List<ChatRoomDto> getJoinedChatRooms(User user){
        return chatRoomRepository.findAllByMembersContaining(user).stream().map(ChatRoom::toChatRoomDto).toList();
    }
}
