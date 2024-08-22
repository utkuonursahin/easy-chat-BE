package me.utku.easychatbe.user;

public record UserRegisterDto(
        String username,
        String password,
        String email
) {
}
