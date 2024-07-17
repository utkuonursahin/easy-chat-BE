package me.utku.easychatbe.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import me.utku.easychatbe.dto.MessageDto;
import me.utku.easychatbe.service.MessageService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketModule {
    private final SocketIOServer socketIOServer;
    private final MessageService messageService;

    public SocketModule(SocketIOServer socketIOServer, MessageService messageService) {
        this.socketIOServer = socketIOServer;
        this.messageService = messageService;

        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", MessageDto.class, onMessageReceived());
    }

    private ConnectListener onConnected() {
        return client -> {
            String roomId = client.getHandshakeData().getSingleUrlParam("room-id");
            client.joinRoom(roomId);
            log.info(String.format("SockedID: %s connected", client.getSessionId().toString()));
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info(String.format("SockedID: %s disconnected", client.getSessionId().toString()));
            client.disconnect();
        };
    }

    private DataListener<MessageDto> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(String.format("Message received from %s: %s", senderClient.getSessionId().toString(), data.content()));
            String roomId = senderClient.getHandshakeData().getSingleUrlParam("room-id");
            this.messageService.createEntity(data);
            senderClient.getNamespace().getRoomOperations(roomId).getClients().stream()
                    .filter(c -> !c.getSessionId().equals(senderClient.getSessionId()))
                    .forEach(x -> x.sendEvent("get_message", data));
        };
    }
}
