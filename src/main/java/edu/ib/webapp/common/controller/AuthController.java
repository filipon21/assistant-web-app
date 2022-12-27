package edu.ib.webapp.common.controller;

import edu.ib.webapp.common.model.request.AuthRequest;
import edu.ib.webapp.common.model.response.AuthResponse;
import edu.ib.webapp.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler Springowy z metodą do autoryzacji.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Metoda służąca do autoryzacji,
     * @param authRequest - email i hasło użytkownika
     * @return AuthResponse - dane użytkownika i utworzony JW Token jako JSON
     * @throws Exception
     */
    @PostMapping({"/authenticate"})
    public AuthResponse createJwtToken(@RequestBody AuthRequest authRequest) throws Exception {
        return authService.createJwtToken(authRequest);
    }
}
