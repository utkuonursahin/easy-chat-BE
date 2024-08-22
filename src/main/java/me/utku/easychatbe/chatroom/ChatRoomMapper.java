package me.utku.easychatbe.chatroom;

import me.utku.easychatbe.model.ChatRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomDto toChatRoomDto(ChatRoom chatRoom);

    ChatRoom toChatRoom(ChatRoomDto chatRoomDto);
}
