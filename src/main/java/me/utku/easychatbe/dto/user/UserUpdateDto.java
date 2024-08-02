package me.utku.easychatbe.dto.user;

public record UserUpdateDto(String username, String email, String oldPassword, String newPassword,
                            String confirmPassword) {
}
