package me.utku.easychatbe.service;

import me.utku.easychatbe.dto.message.MessageDto;
import me.utku.easychatbe.dto.message.PaginatedMessageDto;
import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.model.Message;
import me.utku.easychatbe.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MessageService implements BaseService<MessageDto> {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageDto getEntityById(UUID id) {
        Message message = this.messageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return message.toMessageDto();
    }

    @Override
    public List<MessageDto> getAllEntities() {
        return this.messageRepository.findAll().stream().map(Message::toMessageDto).toList();
    }

    @Override
    public MessageDto createEntity(MessageDto entityDto) {
        return this.messageRepository.save(entityDto.toMessage()).toMessageDto();
    }

    @Override
    public MessageDto updateEntity(UUID id, MessageDto updateEntityDto) {
        if (!existsById(id)) throw new EntityNotFoundException();
        return this.messageRepository.save(updateEntityDto.toMessage()).toMessageDto();
    }

    @Override
    public void deleteEntity(UUID id) {
        if (!existsById(id)) throw new EntityNotFoundException();
        this.messageRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.messageRepository.existsById(id);
    }

    public PaginatedMessageDto getMessagesPageByReceiverId(UUID receiverId, int page, int size) {
        Page<Message> messagePage = this.messageRepository.findAllByReceiver_IdOrderByCreatedAtDesc(receiverId, PageRequest.of(page, size));
        return new PaginatedMessageDto(messagePage.stream().map(Message::toMessageDto).toList(), page, size, messagePage.getTotalElements(), messagePage.getTotalPages());
    }
}
