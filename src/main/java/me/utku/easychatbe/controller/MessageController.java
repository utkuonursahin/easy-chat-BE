package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.dto.message.MessageDto;
import me.utku.easychatbe.dto.message.PaginatedMessageDto;
import me.utku.easychatbe.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController extends CrudController<MessageDto> {
    public MessageController(MessageService messageService) {
        super(messageService);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<GenericResponse<PaginatedMessageDto>> getMessagesPageByReceiver(@PathVariable UUID receiverId,
                                                                                          @RequestParam int page,
                                                                                          @RequestParam int size) {
        PaginatedMessageDto paginatedMessageDto = ((MessageService) this.entityService).getMessagesPageByReceiverId(receiverId, page, size);
        return new GenericResponse<>(HttpStatus.OK.value(), "Messages fetched successfully", paginatedMessageDto).toResponseEntity();
    }
}
