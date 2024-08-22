package me.utku.easychatbe.message;

import java.util.List;

public record PaginatedMessageDto(List<MessageDto> content, int page, int size, long totalElements, long totalPages) {
}
