package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import me.utku.easychatbe.dto.ChatRoomDto;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ChatRoom extends BaseEntity {
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> members;

    public ChatRoom(){
        super();
    }

    public ChatRoom(String name, User createdBy, List<User> members) {
        super();
        this.name = name;
        this.createdBy = createdBy;
        this.members = members;
    }

    public ChatRoomDto toChatRoomDto(){
        return new ChatRoomDto(this.getId(), this.name, this.createdBy.toUserDto(), this.members.stream().map(User::toUserDto).toList());
    }
}
