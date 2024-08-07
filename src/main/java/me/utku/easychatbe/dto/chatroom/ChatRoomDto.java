package me.utku.easychatbe.dto.chatroom;

import me.utku.easychatbe.dto.user.UserDto;

import java.util.List;
import java.util.UUID;

public record ChatRoomDto(
        UUID id,
        String name,
        UserDto createdBy,
        List<UserDto> members
) {
}
