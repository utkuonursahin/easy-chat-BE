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
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return chatRoom.toChatRoomDto();
    }

    @Override
    public List<ChatRoomDto> getAllEntities() {
        return chatRoomRepository.findAll().stream().map(ChatRoom::toChatRoomDto).toList();
    }

    @Override
    public ChatRoomDto createEntity(ChatRoomDto entityDto) {
        User authUser = authService.getAuthenticatedUser();
        return chatRoomRepository.save(new ChatRoom()
                        .setName(entityDto.name())
                        .setCreatedBy(authUser)
                        .setMembers(List.of(authUser))
        ).toChatRoomDto();
    }

    @Override
    public ChatRoomDto updateEntity(UUID id, ChatRoomDto updateEntityDto) {
        if(!existsById(id)) throw new EntityNotFoundException();
        return chatRoomRepository.save(updateEntityDto.toChatRoom()).toChatRoomDto();
    }

    @Override
    public void deleteEntity(UUID id) {
        if(!existsById(id)) throw new EntityNotFoundException();
        chatRoomRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return chatRoomRepository.existsById(id);
    }

    public List<ChatRoomDto> getJoinedChatRooms(User user){
        return chatRoomRepository.findAllByMembersContaining(user).stream().map(ChatRoom::toChatRoomDto).toList();
    }

    public ChatRoomDto joinChatRoom(UUID chatRoomId, User user){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(EntityNotFoundException::new);
        chatRoom.getMembers().add(user);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom.toChatRoomDto();
    }

    public ChatRoomDto leaveChatRoom(UUID chatRoomId, User user){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(EntityNotFoundException::new);
        chatRoom.getMembers().remove(user);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom.toChatRoomDto();
    }
}
