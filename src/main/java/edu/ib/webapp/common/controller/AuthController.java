package edu.ib.webapp.common.controller;

import edu.ib.webapp.common.model.request.AuthRequest;
import edu.ib.webapp.common.model.response.AuthResponse;
import edu.ib.webapp.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping({"/authenticate"})
    public AuthResponse createJwtToken(@RequestBody AuthRequest authRequest) throws Exception {
        return authService.createJwtToken(authRequest);
    }
}
