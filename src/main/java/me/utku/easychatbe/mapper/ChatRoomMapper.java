package me.utku.easychatbe.mapper;

import me.utku.easychatbe.dto.chatroom.ChatRoomDto;
import me.utku.easychatbe.model.ChatRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomDto toChatRoomDto(ChatRoom chatRoom);

    ChatRoom toChatRoom(ChatRoomDto chatRoomDto);
}
