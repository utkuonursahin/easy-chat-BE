package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"chat_room_id", "members_id"}))
    private List<User> members;

    public ChatRoom() {
        super();
    }

    public ChatRoom(String name, User createdBy, List<User> members) {
        super();
        this.name = name;
        this.createdBy = createdBy;
        this.members = members;
    }
}
