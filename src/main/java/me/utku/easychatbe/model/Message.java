package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import me.utku.easychatbe.dto.MessageDto;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Message extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;
    @ManyToOne(fetch = FetchType.LAZY)
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

    public MessageDto toMessageDto() {
        return new MessageDto(this.getId(), this.getSender().toUserDto(), this.getReceiver().toChatRoomDto(), this.getContent(), this.getCreatedAt());
    }
}
