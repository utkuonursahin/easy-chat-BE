package me.utku.easychatbe.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import me.utku.easychatbe.dto.chatroom.ChatRoomDto;
import me.utku.easychatbe.dto.message.MessageDto;
import me.utku.easychatbe.enums.WebSocketEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketModule {
    public SocketModule(SocketIOServer socketIOServer, SocketService socketService) {

        socketIOServer.addConnectListener(socketService.onSocketConnection());
        socketIOServer.addDisconnectListener(socketService.onSocketDisconnection());
        socketIOServer.addEventListener(WebSocketEvent.JOIN_ROOM.getEvent(), ChatRoomDto.class, socketService.onRoomJoined());
        socketIOServer.addEventListener(WebSocketEvent.LEAVE_ROOM.getEvent(), ChatRoomDto.class, socketService.onRoomLeaved());
        socketIOServer.addEventListener(WebSocketEvent.SEND_MESSAGE.getEvent(), MessageDto.class, socketService.onMessageReceived());
    }
}
