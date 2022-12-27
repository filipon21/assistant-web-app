package edu.ib.webapp.common.service.implementation;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.common.model.request.AuthRequest;
import edu.ib.webapp.common.model.response.AuthResponse;
import edu.ib.webapp.user.mapper.UserMapper;
import edu.ib.webapp.user.model.response.UserResponse;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.common.util.JwtUtil;
import edu.ib.webapp.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa służaca do obsługi logiki związanej z autoryzacją i autentykacją użytkownika.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Metoda służaca do tworzenia JSON Web Tokena.
     * @param authRequest - przyjmuje email i hasło
     * @return AuthResponse - dane użytkownika i token
     * @throws Exception
     */
    @Transactional
    public AuthResponse createJwtToken(AuthRequest authRequest) throws Exception {
        String userName = authRequest.getUserName();
        String userPassword = authRequest.getUserPassword();
        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUserName(userName).orElse(null);
        if (user == null){
            throw new UserException(HttpStatus.NOT_FOUND, ExceptionMessage.USER_NOT_FOUND);
        }

        UserResponse userResponse = userMapper.userToUserResponse(user);

        return new AuthResponse(userResponse, newGeneratedToken);
    }

    /**
     * Metoda służąca do znajdowania użytkownika po nazwie użytkownika (email)
     * @param username - email ( nazwa użytkownika)
     * @return UserDetails - email, hasło, rola
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElse(null);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    /**
     * Metoda słuzaca do znajdowania ról użytkownika
     * @param user - dane użytkownika
     * @return Lista ról
     */
    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    /**
     * Metoda służąca do autentykacji użytkownika (logowania)
     * @param userName - nazwa użytkownika (email)
     * @param userPassword - hasło
     * @throws Exception
     */
    public void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
