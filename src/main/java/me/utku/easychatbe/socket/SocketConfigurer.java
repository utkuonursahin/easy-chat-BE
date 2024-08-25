package me.utku.easychatbe.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import me.utku.easychatbe.chatroom.ChatRoomDto;
import me.utku.easychatbe.enums.WebSocketEvent;
import me.utku.easychatbe.message.MessageDto;
import me.utku.easychatbe.user.UserDto;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketConfigurer {
    public SocketConfigurer(SocketIOServer socketIOServer, SocketService socketService) {
        socketIOServer.addConnectListener(socketService.onSocketConnection());
        socketIOServer.addDisconnectListener(socketService.onSocketDisconnection());
        socketIOServer.addEventListener(WebSocketEvent.JOIN_ROOM.getEvent(), ChatRoomDto.class, socketService.onRoomJoined());
        socketIOServer.addEventListener(WebSocketEvent.LEAVE_ROOM.getEvent(), ChatRoomDto.class, socketService.onRoomLeaved());
        socketIOServer.addEventListener(WebSocketEvent.SEND_MESSAGE.getEvent(), MessageDto.class, socketService.onMessageReceived());
        socketIOServer.addEventListener(WebSocketEvent.TYPING.getEvent(), UserDto.class, socketService.onTyping());
        socketIOServer.addEventListener(WebSocketEvent.STOP_TYPING.getEvent(), UserDto.class, socketService.onStopTyping());
    }
}
