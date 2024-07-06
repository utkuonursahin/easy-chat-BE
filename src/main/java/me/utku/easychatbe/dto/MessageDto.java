package me.utku.easychatbe.dto;

import java.util.List;
import java.util.UUID;

public record MessageDto(UUID id, UserDto sender, ChatRoomDto receiver, List<UserDto> seenBy, String content) { }
