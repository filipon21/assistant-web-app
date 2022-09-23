package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.model.request.UserRequest;
import edu.ib.webapp.user.model.response.UserResponse;
import edu.ib.webapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping({"/register"})
    public UserResponse registerNewUser(@RequestBody @Valid UserRequest user) {
        return userService.registerNewUser(user);
    }

    @PatchMapping("{id}/online")
    public UserResponse updateStatus(@RequestParam @Valid Boolean isOnline, @PathVariable Long id){
        return userService.updateStatus(isOnline, id);
    }

}
