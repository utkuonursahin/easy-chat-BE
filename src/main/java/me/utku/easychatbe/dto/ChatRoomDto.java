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
        return ChatRoom.builder()
                .id(id)
                .name(name)
                .createdBy(createdBy.toUser())
                .members(members.stream().map(UserDto::toUser).toList())
                .isVisible(true)
                .build();
    }
}
