package me.utku.easychatbe.dto;

public record UserRegisterDto(
        String username,
        String password,
        String email
) { }
