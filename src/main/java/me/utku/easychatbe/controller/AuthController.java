package me.utku.easychatbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.utku.easychatbe.dto.AuthRequest;
import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.dto.UserDto;
import me.utku.easychatbe.dto.UserRegisterDto;
import me.utku.easychatbe.model.User;
import me.utku.easychatbe.service.AuthService;
import me.utku.easychatbe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<GenericResponse<UserDto>> isAuthenticated(@AuthenticationPrincipal User user){
        return authService.checkIsAuthenticated(user).toResponseEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<UserDto>> login(@RequestBody AuthRequest authRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        UserDto userDto = authService.authenticate(authRequest, httpServletRequest, httpServletResponse);
        return new GenericResponse<>(HttpStatus.OK.value(), "Login successful", userDto).toResponseEntity();
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<UserDto>> signup(@RequestBody UserRegisterDto userRegisterDto) {
        UserDto userDto = userService.registerUser(userRegisterDto);
        return new GenericResponse<>(HttpStatus.CREATED.value(), "User created", userDto).toResponseEntity();
    }
}
