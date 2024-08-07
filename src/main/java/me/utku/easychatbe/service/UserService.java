package me.utku.easychatbe.service;

import me.utku.easychatbe.dto.user.UserDto;
import me.utku.easychatbe.dto.user.UserRegisterDto;
import me.utku.easychatbe.dto.user.UserUpdateDto;
import me.utku.easychatbe.enums.Role;
import me.utku.easychatbe.exception.EntityNotFoundException;
import me.utku.easychatbe.exception.FeatureNotSupportedException;
import me.utku.easychatbe.exception.PasswordIsIncorrectForChangeException;
import me.utku.easychatbe.mapper.UserMapper;
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
public class UserService implements BaseService<UserDto>, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getEntityById(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllEntities() {
        return this.userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public UserDto createEntity(UserDto entityDto) {
        throw new FeatureNotSupportedException("User creation with this route is not supported. Please use /api/auth/register");
    }

    @Override
    public UserDto updateEntity(UUID id, UserDto updateEntityDto) {
        if (!existsById(id)) throw new EntityNotFoundException();
        User updatedUser = this.userRepository.save(userMapper.toUser(updateEntityDto));
        return userMapper.toUserDto(updatedUser);
    }

    @Override
    public void deleteEntity(UUID id) {
        if (!existsById(id)) throw new EntityNotFoundException();
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Override
    public boolean existsById(UUID id) {
        return this.userRepository.existsById(id);
    }

    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        User registeredUser = userRepository.save(new User()
                .setUsername(userRegisterDto.username())
                .setPassword(bCryptPasswordEncoder.encode(userRegisterDto.password()))
                .setEmail(userRegisterDto.email())
                .setAuthorities(List.of(Role.ROLE_USER))
        );
        return userMapper.toUserDto(registeredUser);
    }

    public UserDto updateMe(User user, UserUpdateDto userUpdateDto) {
        user.setUsername(userUpdateDto.username());
        user.setEmail(userUpdateDto.email());
        if (!userUpdateDto.newPassword().isEmpty()) this.updatePassword(user, userUpdateDto);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDto(updatedUser);
    }

    private void updatePassword(User user, UserUpdateDto userUpdateDto) {
        if (!bCryptPasswordEncoder.matches(userUpdateDto.oldPassword(), user.getPassword())) {
            throw new PasswordIsIncorrectForChangeException();
        } else user.setPassword(bCryptPasswordEncoder.encode(userUpdateDto.newPassword()));
    }
}
