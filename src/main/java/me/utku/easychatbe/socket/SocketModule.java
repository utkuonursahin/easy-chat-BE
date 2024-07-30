package me.utku.easychatbe.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import me.utku.easychatbe.dto.ChatRoomDto;
import me.utku.easychatbe.dto.MessageDto;
import me.utku.easychatbe.service.MessageService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketModule {
    private final SocketIOServer socketIOServer;
    private final UserRoomTracker userRoomTracker;
    private final MessageService messageService;

    public SocketModule(SocketIOServer socketIOServer, UserRoomTracker userRoomTracker, MessageService messageService) {
        this.socketIOServer = socketIOServer;
        this.userRoomTracker = userRoomTracker;
        this.messageService = messageService;

        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", MessageDto.class, onMessageReceived());
        socketIOServer.addEventListener("join_room", ChatRoomDto.class, onRoomJoined());
        socketIOServer.addEventListener("leave_room", ChatRoomDto.class, onRoomLeft());
    }

    private ConnectListener onConnected() {
        return client -> log.info(String.format("SockedID: %s connected", client.getSessionId().toString()));
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            userRoomTracker.removeUserRoom(client.getSessionId().toString());
            log.info(String.format("SockedID: %s disconnected", client.getSessionId().toString()));
            client.disconnect();
        };
    }

    private DataListener<ChatRoomDto> onRoomJoined() {
        return (client, data, ackSender) -> {
            log.info(String.format("Room joined: %s", data.id()));
            client.joinRoom(data.id().toString());
            userRoomTracker.setUserRoom(client.getSessionId().toString(), data.id().toString());
        };
    }

    private DataListener<ChatRoomDto> onRoomLeft() {
        return (client, data, ackSender) -> {
            userRoomTracker.removeUserRoom(client.getSessionId().toString());
            log.info(String.format("Room left: %s", data.id()));
            client.leaveRoom(data.id().toString());
        };
    }

    private DataListener<MessageDto> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(String.format("Message received from %s: %s", senderClient.getSessionId().toString(), data.content()));
            String roomId = userRoomTracker.getUserRoom(senderClient.getSessionId().toString());
            MessageDto savedMessage = this.messageService.createEntity(data);
            senderClient.getNamespace().getRoomOperations(roomId).sendEvent("get_message", savedMessage);
        };
    }
}
