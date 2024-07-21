package me.utku.easychatbe.socket;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRoomTracker {
    private final ConcurrentHashMap<String, String> userRoomMap = new ConcurrentHashMap<>();

    public void setUserRoom(String sessionId, String roomId) {
        userRoomMap.put(sessionId, roomId);
    }

    public String getUserRoom(String sessionId) {
        return userRoomMap.get(sessionId);
    }

    public void removeUserRoom(String sessionId) {
        userRoomMap.remove(sessionId);
    }
}
