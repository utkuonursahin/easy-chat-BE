package me.utku.easychatbe.user;

import me.utku.easychatbe.generic.CrudController;
import me.utku.easychatbe.generic.GenericResponse;
import me.utku.easychatbe.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends CrudController<UserDto> {
    public UserController(UserService userService) {
        super(userService);
    }

    @PatchMapping("/me")
    public ResponseEntity<GenericResponse<UserDto>> updateMe(@AuthenticationPrincipal User user, @RequestBody UserUpdateDto userUpdateDto) {
        UserDto updatedUser = ((UserService) this.entityService).updateMe(user, userUpdateDto);
        return new GenericResponse<>(HttpStatus.OK.value(), "Your details updated successfully", updatedUser).toResponseEntity();
    }
}
