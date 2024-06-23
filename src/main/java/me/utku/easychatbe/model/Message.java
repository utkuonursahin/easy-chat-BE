package me.utku.easychatbe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "messages")
@Builder
@Data
public class Message extends BaseModel {
    @ManyToOne
    private User sender;
    @ManyToOne
    private ChatRoom receiver;
    private String content;

    public Message() {
        super();
    }

    public Message(User sender, ChatRoom receiver, String content) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
