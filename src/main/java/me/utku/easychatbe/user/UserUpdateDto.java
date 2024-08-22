package me.utku.easychatbe.user;

public record UserUpdateDto(String username, String email, String oldPassword, String newPassword,
                            String confirmPassword) {
}
