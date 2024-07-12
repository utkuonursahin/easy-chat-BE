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
        return Message.builder()
                .id(id)
                .sender(sender.toUser())
                .receiver(receiver.toChatRoom())
                .seenBy(seenBy.stream().map(UserDto::toUser).toList())
                .content(content)
                .isVisible(true)
                .build();
    }
}
