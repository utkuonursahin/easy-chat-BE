package me.utku.easychatbe.dto.message;

import me.utku.easychatbe.dto.chatroom.ChatRoomDto;
import me.utku.easychatbe.dto.user.UserDto;

import java.util.UUID;

public record MessageDto(
        UUID id,
        UserDto sender,
        ChatRoomDto receiver,
        String content,
        String createdAt
) {
}
