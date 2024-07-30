package me.utku.easychatbe.dto;

import me.utku.easychatbe.enums.Role;
import me.utku.easychatbe.model.User;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        List<Role> authorities
) {
    public User toUser() {
        User user = new User()
                .setUsername(username)
                .setEmail(email)
                .setAuthorities(authorities);
        user.setId(id);
        return user;
    }
}
