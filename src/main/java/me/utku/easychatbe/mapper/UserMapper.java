package me.utku.easychatbe.mapper;

import me.utku.easychatbe.dto.user.UserDto;
import me.utku.easychatbe.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
