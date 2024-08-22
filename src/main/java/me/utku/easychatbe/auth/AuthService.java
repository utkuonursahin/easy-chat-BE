package me.utku.easychatbe.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.utku.easychatbe.generic.GenericResponse;
import me.utku.easychatbe.user.UserDto;
import me.utku.easychatbe.user.UserMapper;
import me.utku.easychatbe.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final UserMapper userMapper;

    public AuthService(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.userMapper = userMapper;
    }

    public UserDto authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        UserDto userDto = null;
        try {
            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.email(), authRequest.password());
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                userDto = userMapper.toUserDto(((User) authentication.getPrincipal()));
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Failed authentication with USERNAME:" + authRequest.email());
        }
        return userDto;
    }

    public GenericResponse<UserDto> checkIsAuthenticated(User user) {
        if (user == null)
            return new GenericResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
        else
            return new GenericResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userMapper.toUserDto(user));
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
