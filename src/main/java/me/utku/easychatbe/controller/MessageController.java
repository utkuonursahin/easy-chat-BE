package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.model.BaseModel;
import me.utku.easychatbe.model.Message;
import me.utku.easychatbe.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController implements BaseController<Message>{
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @Override
    public ResponseEntity<GenericResponse<List<Message>>> getAll() {
        List<Message> messageList = messageService.getAllEntities();
        return new GenericResponse<>(HttpStatus.OK.value(), "Messages fetched successfully", messageList).toResponseEntity();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<Message>> getById(@PathVariable UUID id) {
        Message message = messageService.getEntityById(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Message fetched successfully", message).toResponseEntity();
    }

    @PostMapping
    @Override
    public ResponseEntity<GenericResponse<Message>> create(@RequestBody Object data) {
        Message message = messageService.createEntity((Message)data);
        return new GenericResponse<>(HttpStatus.CREATED.value(), "Message created successfully", message).toResponseEntity();
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<Message>> update(@PathVariable UUID id, @RequestBody Object updateData) {
        Message message = messageService.updateEntity(id, (Message)updateData);
        return new GenericResponse<>(HttpStatus.OK.value(), "Message updated successfully", message).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id) {
        messageService.deleteEntity(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Message deleted successfully", true).toResponseEntity();
    }
}
