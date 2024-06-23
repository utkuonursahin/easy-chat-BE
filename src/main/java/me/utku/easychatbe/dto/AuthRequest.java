package me.utku.easychatbe.dto;

public record AuthRequest(
        String username,
        String password) { }
