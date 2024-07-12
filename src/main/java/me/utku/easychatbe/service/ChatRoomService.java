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
        if (chatRoom == null) throw new EntityNotFoundException();
        return chatRoom.toChatRoomDto();
    }

    @Override
    public List<ChatRoomDto> getAllEntities() {
        return chatRoomRepository.findAll().stream().map(ChatRoom::toChatRoomDto).toList();
    }

    @Override
    public ChatRoomDto createEntity(ChatRoomDto entityDto) {
        User authUser = authService.getAuthenticatedUser();
        return chatRoomRepository.save(ChatRoom.builder()
                        .name(entityDto.name())
                        .createdBy(authUser)
                        .members(List.of(authUser))
                        .isVisible(true)
                        .build()
        ).toChatRoomDto();
    }

    @Override
    public ChatRoomDto updateEntity(UUID id, ChatRoomDto updateEntityDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        chatRoom = chatRoomRepository.save(updateEntityDto.toChatRoom());
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

    public ChatRoomDto joinChatRoom(UUID chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
        if (chatRoom == null) throw new EntityNotFoundException();
        User authUser = authService.getAuthenticatedUser();
        chatRoom.getMembers().add(authUser);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom.toChatRoomDto();
    }
}
