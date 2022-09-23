package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.service.implementation.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleServiceImpl roleServiceImpl;

    @PostMapping({"/create"})
    public Role createNewRole(@RequestBody Role role) {
        return roleServiceImpl.createNewRole(role);
    }
}
