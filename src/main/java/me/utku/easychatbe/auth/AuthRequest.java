package me.utku.easychatbe.auth;

public record AuthRequest(
        String email,
        String password
) {
}
