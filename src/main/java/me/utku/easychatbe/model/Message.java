package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "messages")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom receiver;
    @ManyToMany(fetch = FetchType.LAZY)
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
