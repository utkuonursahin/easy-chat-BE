package me.utku.easychatbe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.utku.easychatbe.dto.AuthRequest;
import me.utku.easychatbe.dto.GenericResponse;
import me.utku.easychatbe.dto.UserDto;
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

    public AuthService(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    public UserDto authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        UserDto userDto = null;
        try{
            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.username(),authRequest.password());
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                userDto = ((User)authentication.getPrincipal()).toUserDto();
            }
        }catch (Exception e){
            throw new BadCredentialsException("Failed authentication with USERNAME:"+authRequest.username());
        }
        return userDto;
    }

    public GenericResponse<UserDto> checkIsAuthenticated(User user){
        if(user == null) return new GenericResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),null);
        else return new GenericResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),user.toUserDto());
    }

    public User getAuthenticatedUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
