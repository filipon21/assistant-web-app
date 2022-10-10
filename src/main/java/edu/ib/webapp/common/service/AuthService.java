package edu.ib.webapp.common.service;

import edu.ib.webapp.common.model.request.AuthRequest;
import edu.ib.webapp.common.model.response.AuthResponse;
import edu.ib.webapp.user.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public interface AuthService {

    AuthResponse createJwtToken(AuthRequest jwtRequest) throws Exception;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Set<SimpleGrantedAuthority> getAuthority(User user);

    void authenticate(String userName, String userPassword) throws Exception;
}
