package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.UserDto;
import me.utku.easychatbe.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends CrudController<UserDto> {
    public UserController(UserService userService) {
        super(userService);
    }
}
