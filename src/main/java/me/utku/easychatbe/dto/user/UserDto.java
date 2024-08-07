package me.utku.easychatbe.dto.user;

import me.utku.easychatbe.enums.Role;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        List<Role> authorities
) {
}
