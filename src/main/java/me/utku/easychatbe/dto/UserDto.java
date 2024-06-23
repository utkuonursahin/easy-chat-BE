package me.utku.easychatbe.dto;

import me.utku.easychatbe.enums.Role;

import java.util.List;

public record UserDto(
        String username,
        String email,
        List<Role> authorities
) {}
