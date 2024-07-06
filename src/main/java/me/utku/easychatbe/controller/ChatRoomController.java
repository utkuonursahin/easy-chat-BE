package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.ChatRoomDto;
import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController extends CrudController<ChatRoomDto> {
    public ChatRoomController(ChatRoomService chatRoomService) {
        super(chatRoomService);
    }

    @GetMapping("/joined")
    public ResponseEntity<GenericResponse<List<ChatRoomDto>>> getJoinedChatRooms(@AuthenticationPrincipal User user){
        List<ChatRoomDto> chatRooms = ((ChatRoomService)this.entityService).getJoinedChatRooms(user);
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat rooms fetched successfully", chatRooms).toResponseEntity();
    }
}
