package me.utku.easychatbe.service;

import me.utku.easychatbe.dto.MessageDto;
import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.model.Message;
import me.utku.easychatbe.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService implements BaseService<MessageDto> {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageDto getEntityById(UUID id) {
        Message message = this.messageRepository.findById(id).orElse(null);
        if(message == null) throw new EntityNotFoundException();
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
        MessageDto message = this.getEntityById(id);
        if(message == null) throw new EntityNotFoundException();
        return this.messageRepository.save(updateEntityDto.toMessage()).toMessageDto();
    }

    @Override
    public void deleteEntity(UUID id) {
        MessageDto message = this.getEntityById(id);
        if(message == null) throw new EntityNotFoundException();
        this.messageRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.messageRepository.existsById(id);
    }

    public Page<List<MessageDto>> getMessagesPageByReceiverId(UUID receiverId, int page, int size) {
        return this.messageRepository.findAllByReceiver_IdOrderByCreatedAtDesc(receiverId, PageRequest.of(page,size))
                .map(messages -> messages.stream().map(Message::toMessageDto).toList());
    }
}
