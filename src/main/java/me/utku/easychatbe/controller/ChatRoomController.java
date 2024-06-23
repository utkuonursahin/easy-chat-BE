package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.model.BaseModel;
import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController implements BaseController<ChatRoom>{
    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping
    @Override
    public ResponseEntity<GenericResponse<List<ChatRoom>>> getAll() {
        List<ChatRoom> chatRooms = chatRoomService.getAllEntities();
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat rooms fetched successfully", chatRooms).toResponseEntity();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<ChatRoom>> getById(@PathVariable UUID id) {
        ChatRoom chatRoom = chatRoomService.getEntityById(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat room fetched successfully", chatRoom).toResponseEntity();
    }

    @PostMapping
    @Override
    public ResponseEntity<GenericResponse<ChatRoom>> create(@RequestBody BaseModel data) {
        ChatRoom chatRoom = chatRoomService.createEntity((ChatRoom)data);
        return new GenericResponse<>(HttpStatus.CREATED.value(), "Chat room created successfully", chatRoom).toResponseEntity();
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<ChatRoom>> update(@PathVariable UUID id, @RequestBody BaseModel updateData) {
        ChatRoom chatRoom = chatRoomService.updateEntity(id, (ChatRoom)updateData);
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat room updated successfully", chatRoom).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id) {
        chatRoomService.deleteEntity(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat room deleted successfully", true).toResponseEntity();
    }
}
