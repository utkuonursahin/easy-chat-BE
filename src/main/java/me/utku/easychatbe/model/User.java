package me.utku.easychatbe.model;

import jakarta.persistence.*;
import lombok.*;
import me.utku.easychatbe.dto.UserDto;
import me.utku.easychatbe.enums.Role;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Table(name = "users")
@DynamicUpdate
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String email;
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name= "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Role> authorities = List.of(Role.ROLE_USER);
    private boolean isEnabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    public User() {
        super();
    }

    public User(String username, String email, String password, List<Role> authorities, boolean isEnabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isEnabled = isEnabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public UserDto toUserDto(){
        return new UserDto(this.getId(),username, email, List.copyOf(authorities));
    }
}
