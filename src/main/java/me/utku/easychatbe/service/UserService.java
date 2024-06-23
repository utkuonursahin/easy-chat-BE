package me.utku.easychatbe.service;

import me.utku.easychatbe.enums.Role;
import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements BaseService<User>,UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getEntityById(UUID id) {
        User user = this.userRepository.findById(id).orElse(null);
        if(user == null) throw new EntityNotFoundException();
        return user;
    }

    @Override
    public List<User> getAllEntities() {
        return this.userRepository.findAll();
    }

    @Override
    public User createEntity(User entity) {
        return userRepository.save(User.builder()
                .username(entity.getUsername())
                .password(bCryptPasswordEncoder.encode(entity.getPassword()))
                .email(entity.getEmail())
                .authorities(List.of(Role.ROLE_USER))
                .isEnabled(entity.isEnabled())
                .accountNonExpired(entity.isAccountNonExpired())
                .accountNonLocked(entity.isAccountNonLocked())
                .credentialsNonExpired(entity.isCredentialsNonExpired())
                .build()
        );
    }

    @Override
    public User updateEntity(UUID id,User newEntity) {
        User user = this.getEntityById(id);
        if(user == null) throw new EntityNotFoundException();
        return this.userRepository.save(newEntity);
    }

    @Override
    public void deleteEntity(UUID id) {
        User user = this.getEntityById(id);
        if(user == null) throw new EntityNotFoundException();
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
