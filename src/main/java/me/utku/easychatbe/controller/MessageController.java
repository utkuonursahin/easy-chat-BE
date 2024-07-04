package me.utku.easychatbe.controller;

import me.utku.easychatbe.model.Message;
import me.utku.easychatbe.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController extends CrudController<Message> {
    public MessageController(MessageService messageService) {
        super(messageService);
    }
}
