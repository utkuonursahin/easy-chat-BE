package me.utku.easychatbe.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebSocketEvent {
    JOIN_ROOM("join_room"),
    LEAVE_ROOM("leave_room"),
    SEND_MESSAGE("send_message"),
    GET_MESSAGE("get_message"),
    TYPING("typing"),
    STOP_TYPING("stop_typing");

    private final String event;
}
