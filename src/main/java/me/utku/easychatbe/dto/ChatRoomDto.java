package me.utku.easychatbe.dto;

import java.util.List;
import java.util.UUID;

public record ChatRoomDto(UUID id, String name, UserDto createdBy, List<UserDto> members) {}
