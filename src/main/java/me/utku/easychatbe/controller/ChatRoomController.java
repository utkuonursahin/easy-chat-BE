package me.utku.easychatbe.controller;

import me.utku.easychatbe.model.ChatRoom;
import me.utku.easychatbe.service.ChatRoomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController extends CrudController<ChatRoom> {
    public ChatRoomController(ChatRoomService chatRoomService) {
        super(chatRoomService);
    }
}
