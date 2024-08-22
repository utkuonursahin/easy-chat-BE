package me.utku.easychatbe.message;

import me.utku.easychatbe.chatroom.ChatRoomDto;
import me.utku.easychatbe.user.UserDto;

import java.util.UUID;

public record MessageDto(
        UUID id,
        UserDto sender,
        ChatRoomDto receiver,
        String content,
        String createdAt
) {
}
