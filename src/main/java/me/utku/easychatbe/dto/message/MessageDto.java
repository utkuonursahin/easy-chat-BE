package me.utku.easychatbe.dto.message;

import me.utku.easychatbe.dto.chatroom.ChatRoomDto;
import me.utku.easychatbe.dto.user.UserDto;
import me.utku.easychatbe.model.Message;

import java.util.UUID;

public record MessageDto(
        UUID id,
        UserDto sender,
        ChatRoomDto receiver,
        String content,
        String createdAt
) {
    public Message toMessage() {
        Message message = new Message()
                .setSender(sender.toUser())
                .setReceiver(receiver.toChatRoom())
                .setContent(content);
        message.setId(id);
        return message;
    }
}
