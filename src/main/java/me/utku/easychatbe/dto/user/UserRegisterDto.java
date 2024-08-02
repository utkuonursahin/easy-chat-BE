package me.utku.easychatbe.dto.user;

public record UserRegisterDto(
        String username,
        String password,
        String email
) {
}
