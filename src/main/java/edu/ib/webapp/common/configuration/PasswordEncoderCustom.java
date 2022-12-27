package edu.ib.webapp.common.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Komponent (bean) Springowy. Klasa służy do dekodowania hasła użytkownika z bazy danych.
 */
@Component
@RequiredArgsConstructor
public class PasswordEncoderCustom {

    private final PasswordEncoder passwordEncoder;

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
