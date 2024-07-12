package me.utku.easychatbe.service;

import me.utku.easychatbe.dto.UserDto;
import me.utku.easychatbe.dto.UserRegisterDto;
import me.utku.easychatbe.enums.Role;
import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.exception.FeatureNotSupportedException;
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
public class UserService implements BaseService<UserDto>,UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto getEntityById(UUID id) {
        User user = this.userRepository.findById(id).orElse(null);
        if(user == null) throw new EntityNotFoundException();
        return user.toUserDto();
    }

    @Override
    public List<UserDto> getAllEntities() {
        return this.userRepository.findAll().stream().map(User::toUserDto).toList();
    }

    @Override
    public UserDto createEntity(UserDto entityDto) {
        throw new FeatureNotSupportedException("User creation with this route is not supported. Please use /api/auth/register");
    }

    @Override
    public UserDto updateEntity(UUID id,UserDto updateEntityDto) {
        UserDto user = this.getEntityById(id);
        if(user == null) throw new EntityNotFoundException();
        return this.userRepository.save(updateEntityDto.toUser()).toUserDto();
    }

    @Override
    public void deleteEntity(UUID id) {
        UserDto user = this.getEntityById(id);
        if(user == null) throw new EntityNotFoundException();
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public UserDto registerUser(UserRegisterDto userRegisterDto){
        return userRepository.save(User.builder()
                .username(userRegisterDto.username())
                .password(bCryptPasswordEncoder.encode(userRegisterDto.password()))
                .email(userRegisterDto.email())
                .authorities(List.of(Role.ROLE_USER))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .isVisible(true)
                .build()
        ).toUserDto();
    }
}
