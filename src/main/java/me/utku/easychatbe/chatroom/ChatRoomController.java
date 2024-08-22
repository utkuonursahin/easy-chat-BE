package me.utku.easychatbe.chatroom;

import me.utku.easychatbe.generic.CrudController;
import me.utku.easychatbe.generic.GenericResponse;
import me.utku.easychatbe.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController extends CrudController<ChatRoomDto> {
    public ChatRoomController(ChatRoomService chatRoomService) {
        super(chatRoomService);
    }

    @GetMapping("/join")
    public ResponseEntity<GenericResponse<List<ChatRoomDto>>> getJoinedChatRooms(@AuthenticationPrincipal User user) {
        List<ChatRoomDto> chatRooms = ((ChatRoomService) this.entityService).getJoinedChatRooms(user);
        return new GenericResponse<>(HttpStatus.OK.value(), "Chat rooms fetched successfully", chatRooms).toResponseEntity();
    }

    @PostMapping("/join/{chatRoomId}")
    public ResponseEntity<GenericResponse<ChatRoomDto>> joinChatRoom(@PathVariable UUID chatRoomId, @AuthenticationPrincipal User user) {
        ChatRoomDto chatRoomDto = ((ChatRoomService) this.entityService).joinChatRoom(chatRoomId, user);
        return new GenericResponse<>(HttpStatus.OK.value(), "Joined to chat room successfully", chatRoomDto).toResponseEntity();
    }

    @DeleteMapping("/leave/{chatRoomId}")
    public ResponseEntity<GenericResponse<ChatRoomDto>> leaveChatRoom(@PathVariable UUID chatRoomId, @AuthenticationPrincipal User user) {
        ChatRoomDto chatRoomDto = ((ChatRoomService) this.entityService).leaveChatRoom(chatRoomId, user);
        return new GenericResponse<>(HttpStatus.OK.value(), "Left chat room successfully", chatRoomDto).toResponseEntity();
    }
}
