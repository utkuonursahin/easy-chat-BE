package me.utku.easychatbe.dto;

import me.utku.easychatbe.model.Message;

import java.time.Instant;
import java.util.UUID;

public record MessageDto(
        UUID id,
        UserDto sender,
        ChatRoomDto receiver,
        String content,
        Instant createdAt
) {
    public Message toMessage(){
        Message message = new Message()
                .setSender(sender.toUser())
                .setReceiver(receiver.toChatRoom())
                .setContent(content);
        message.setId(id);
        message.setCreatedAt(createdAt);
        return message;
    }
}
