package me.utku.easychatbe.generic;

import org.springframework.http.ResponseEntity;

public record GenericResponse<T>(int statusCode, String message, T data) {
    public ResponseEntity<GenericResponse<T>> toResponseEntity() {
        return ResponseEntity.status(statusCode).body(this);
    }
}
