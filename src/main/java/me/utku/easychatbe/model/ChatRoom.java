package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "chat_rooms")
@DynamicUpdate
@Builder
@Data
public class ChatRoom extends BaseModel {
    private String name;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User createdBy;
    @ManyToMany
    @JoinTable(
            name = "chat_room_members",
            joinColumns = @JoinColumn(name = "chat_room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id")
    )
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
}
