package me.utku.easychatbe.socket;

import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.utku.easychatbe.chatroom.ChatRoomDto;
import me.utku.easychatbe.enums.WebSocketEvent;
import me.utku.easychatbe.message.MessageDto;
import me.utku.easychatbe.message.MessageService;
import me.utku.easychatbe.user.UserDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocketService {
    private final UserRoomTracker userRoomTracker;
    private final MessageService messageService;

    public ConnectListener onSocketConnection() {
        return client -> log.info(String.format("SockedID: %s connected", client.getSessionId().toString()));
    }

    public DisconnectListener onSocketDisconnection() {
        return client -> {
            userRoomTracker.removeUserRoom(client.getSessionId().toString());
            log.info(String.format("SockedID: %s disconnected", client.getSessionId().toString()));
            client.disconnect();
        };
    }

    public DataListener<ChatRoomDto> onRoomJoined() {
        return (client, data, ackSender) -> {
            log.info(String.format("Room joined: %s", data.id()));
            client.joinRoom(data.id().toString());
            userRoomTracker.setUserRoom(client.getSessionId().toString(), data.id().toString());
        };
    }

    public DataListener<ChatRoomDto> onRoomLeaved() {
        return (client, data, ackSender) -> {
            userRoomTracker.removeUserRoom(client.getSessionId().toString());
            log.info(String.format("Room left: %s", data.id()));
            client.leaveRoom(data.id().toString());
        };
    }

    public DataListener<MessageDto> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(String.format("Message received from %s: %s", senderClient.getSessionId().toString(), data.content()));
            String roomId = userRoomTracker.getUserRoom(senderClient.getSessionId().toString());
            MessageDto savedMessage = this.messageService.createEntity(data);
            senderClient.getNamespace().getRoomOperations(roomId).sendEvent(WebSocketEvent.GET_MESSAGE.getEvent(), savedMessage);
        };
    }

    public DataListener<UserDto> onTyping() {
        return (senderClient, data, ackSender) -> {
            log.info(String.format("User %s is typing", data.username()));
            String roomId = userRoomTracker.getUserRoom(senderClient.getSessionId().toString());
            senderClient.getNamespace().getRoomOperations(roomId).getClients().stream()
                    .filter(client -> !client.getSessionId().toString().equals(senderClient.getSessionId().toString()))
                    .forEach(client -> client.sendEvent(WebSocketEvent.TYPING.getEvent(), data));
        };
    }

    public DataListener<UserDto> onStopTyping() {
        return (senderClient, data, ackSender) -> {
            log.info(String.format("User %s stopped typing", data.username()));
            String roomId = userRoomTracker.getUserRoom(senderClient.getSessionId().toString());
            senderClient.getNamespace().getRoomOperations(roomId).getClients().stream()
                    .filter(client -> !client.getSessionId().toString().equals(senderClient.getSessionId().toString()))
                    .forEach(client -> client.sendEvent(WebSocketEvent.STOP_TYPING.getEvent(), data));
        };
    }
}
