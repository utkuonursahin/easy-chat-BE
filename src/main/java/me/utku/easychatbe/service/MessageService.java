package me.utku.easychatbe.service;

import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.model.Message;
import me.utku.easychatbe.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService implements BaseService<Message> {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message getEntityById(UUID id) {
        Message message = this.messageRepository.findById(id).orElse(null);
        if(message == null) throw new EntityNotFoundException();
        return message;
    }

    @Override
    public List<Message> getAllEntities() {
        return this.messageRepository.findAll();
    }

    @Override
    public Message createEntity(Message entity) {
        return this.messageRepository.save(entity);
    }

    @Override
    public Message updateEntity(UUID id, Message newEntity) {
        Message message = this.getEntityById(id);
        if(message == null) throw new EntityNotFoundException();
        return this.messageRepository.save(newEntity);
    }

    @Override
    public void deleteEntity(UUID id) {
        Message message = this.getEntityById(id);
        if(message == null) throw new EntityNotFoundException();
        this.messageRepository.deleteById(id);
    }
}
