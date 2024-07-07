package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.ChatRoomDto;
import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController extends CrudController<ChatRoom,ChatRoomDto> {
    public ChatRoomController(ChatRoomService chatRoomService) {
        super(chatRoomService);
    }

    @GetMapping("/join")
    public ResponseEntity<GenericResponse<List<ChatRoomDto>>> getJoinedChatRooms(@AuthenticationPrincipal User user){
        List<ChatRoomDto> chatRooms = ((ChatRoomService)this.entityService).getJoinedChatRooms(user);
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat rooms fetched successfully", chatRooms).toResponseEntity();
    }

    @PostMapping("/join/{chatRoomId}")
    public ResponseEntity<GenericResponse<ChatRoomDto>> joinChatRoom(@PathVariable UUID chatRoomId){
        ChatRoomDto chatRoomDto = ((ChatRoomService)this.entityService).joinChatRoom(chatRoomId);
        return new GenericResponse<>(HttpStatus.OK.value(), "Joined to chat room successfully", chatRoomDto).toResponseEntity();
    }
}
