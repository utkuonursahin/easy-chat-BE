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
public class ChatRoomService implements BaseService<ChatRoomDto> {
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
    public ChatRoomDto createEntity(ChatRoomDto entity) {
        User authUser = authService.getAuthenticatedUser();
        ChatRoom chatRoom = ChatRoom.builder()
                .name(entity.name())
                .createdBy(authUser)
                .members(List.of(authUser))
                .build();
        return chatRoomRepository.save(chatRoom).toChatRoomDto();
    }

    @Override
    public ChatRoomDto updateEntity(UUID id, ChatRoomDto newEntity) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        chatRoom = chatRoomRepository.save(ChatRoom.builder().name(newEntity.name()).build());
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
