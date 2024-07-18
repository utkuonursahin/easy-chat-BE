package me.utku.easychatbe.dto;

import java.util.List;

public record PaginatedMessageDto(List<MessageDto> content, int page, int size, long totalElements, long totalPages) {
}
