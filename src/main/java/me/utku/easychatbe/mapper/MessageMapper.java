package me.utku.easychatbe.mapper;

import me.utku.easychatbe.dto.message.MessageDto;
import me.utku.easychatbe.model.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto toMessageDto(Message message);

    Message toMessage(MessageDto messageDto);
}
