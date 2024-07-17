package me.utku.easychatbe.dto;

import me.utku.easychatbe.model.ChatRoom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChatRoomDto(
        UUID id,
        String name,
        UserDto createdBy,
        List<UserDto> members
) {
    public ChatRoom toChatRoom(){
        ChatRoom chatRoom = new ChatRoom()
                .setName(name)
                .setCreatedBy(createdBy.toUser())
                .setMembers(Optional.ofNullable(members).orElse(List.of()).stream().map(UserDto::toUser).toList());
        chatRoom.setId(id);
        return chatRoom;
    }
}
