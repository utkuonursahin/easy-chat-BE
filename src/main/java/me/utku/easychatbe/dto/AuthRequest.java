package me.utku.easychatbe.dto;

public record AuthRequest(
        String email,
        String password
) {
}
