package me.utku.easychatbe.dto;

import me.utku.easychatbe.model.Message;

import java.util.List;
import java.util.UUID;

public record MessageDto(
        UUID id,
        UserDto sender,
        ChatRoomDto receiver,
        List<UserDto> seenBy,
        String content
) {
    public Message toMessage(){
        Message message = new Message()
                .setSender(sender.toUser())
                .setReceiver(receiver.toChatRoom())
                .setSeenBy(seenBy.stream().map(UserDto::toUser).toList())
                .setContent(content);
        message.setId(id);
        return message;
    }
}
