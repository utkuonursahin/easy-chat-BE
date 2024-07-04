package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "messages")
@Builder
@Data
public class Message extends BaseModel {
    @ManyToOne
    private User sender;
    @ManyToOne
    private ChatRoom receiver;
    @ManyToMany
    private List<User> seenBy;
    private String content;

    public Message() {
        super();
    }

    public Message(User sender, ChatRoom receiver, List<User> seenBy , String content) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.seenBy = seenBy;
    }
}
