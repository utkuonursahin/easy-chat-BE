package me.utku.easychatbe.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfig implements CommandLineRunner {
    @Value("${socket-server.host}")
    private String hostname;

    @Value("${socket.server.port}")
    private int port;

    @Bean
    public SocketIOServer socketIOServer(){
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        return new SocketIOServer(config);
    }

    @Override
    public void run(String... args) throws Exception {
        socketIOServer().start();
    }
}
