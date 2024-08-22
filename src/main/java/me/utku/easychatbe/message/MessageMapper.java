package me.utku.easychatbe.message;

import me.utku.easychatbe.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "createdAt", source = "createdAt")
    MessageDto toMessageDto(Message message);

    Message toMessage(MessageDto messageDto);

    default String instantToString(Instant createdAt) {
        return Optional.ofNullable(createdAt).orElse(Instant.now()).toString();
    }
}
