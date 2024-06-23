package me.utku.easychatbe.controller;

import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.dto.UserDto;
import me.utku.easychatbe.model.BaseModel;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController implements BaseController<UserDto> {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Override
    public ResponseEntity<GenericResponse<List<UserDto>>> getAll() {
        List<UserDto> userList = userService.getAllEntities().stream().map(User::toUserDto).toList();
        return new GenericResponse<>(HttpStatus.OK.value(), "Users fetched successfully", userList).toResponseEntity();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<UserDto>> getById(@PathVariable UUID id) {
        User user = userService.getEntityById(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "User fetched successfully", user.toUserDto()).toResponseEntity();
    }

    @PostMapping
    @Override
    public ResponseEntity<GenericResponse<UserDto>> create(@RequestBody BaseModel data) {
        User user = userService.createEntity((User)data);
        return new GenericResponse<>(HttpStatus.CREATED.value(), "User created successfully", user.toUserDto()).toResponseEntity();
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<UserDto>> update(@PathVariable UUID id, @RequestBody BaseModel updateData) {
        User user = userService.updateEntity(id, (User)updateData);
        return new GenericResponse<>(HttpStatus.OK.value(), "User updated successfully", user.toUserDto()).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<GenericResponse<Boolean>> delete(@PathVariable UUID id) {
        userService.deleteEntity(id);
        return new GenericResponse<>(HttpStatus.OK.value(), "User deleted successfully", true).toResponseEntity();
    }
}
