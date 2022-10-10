package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.common.configuration.PasswordEncoderCustom;
import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.RoleException;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.mapper.UserMapper;
import edu.ib.webapp.user.model.request.UserRequest;
import edu.ib.webapp.user.model.request.UserUpdateRequest;
import edu.ib.webapp.user.model.response.UserResponse;
import edu.ib.webapp.user.repository.RoleRepository;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoderCustom passwordEncoder;

    private final UserMapper userMapper;

    @Transactional
    public UserResponse registerNewUser(UserRequest userRequest) {
        User userCheck = userRepository.findByUserName(userRequest.getUserName()).orElse(null);

        if (userCheck != null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USERNAME_MUST_BE_UNIQUE);
        }

        Role role = roleRepository.findByRoleName("USER").orElse(null);
        if (role == null){
            throw new RoleException(HttpStatus.NOT_FOUND, ExceptionMessage.ROLE_NOT_FOUND);
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        User user = new User();

        user.setUserName(userRequest.getUserName());
        user.setPesel(userRequest.getPesel());
        user.setUserFirstName(userRequest.getUserFirstName());
        user.setUserLastName(userRequest.getUserLastName());
        user.setIsActive(true);
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRoles(userRoles);
        user.setUserPassword(passwordEncoder.getEncodedPassword(userRequest.getUserPassword()));
        user.setAddress(userRequest.getAddress());
        user.setVoivodeship(userRequest.getVoivodeship());
        user.setPostalCode(userRequest.getPostalCode());
        user.setCountry(userRequest.getCountry());

        userRepository.save(user);

        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateStatus(Boolean isOnline, Long id) {
        User userCheck = checkIfUserExists(id);

        userCheck.setIsOnline(isOnline);

        userRepository.save(userCheck);

        return userMapper.userToUserResponse(userCheck);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserUpdateRequest userRequest, Long id) {
        User userCheck = checkIfUserExists(id);

        userCheck.setUserName(userRequest.getUserName());
        userCheck.setPesel(userRequest.getPesel());
        userCheck.setUserFirstName(userRequest.getUserFirstName());
        userCheck.setUserLastName(userRequest.getUserLastName());
        userCheck.setPhoneNumber(userRequest.getPhoneNumber());
        userCheck.setAddress(userRequest.getAddress());
        userCheck.setVoivodeship(userRequest.getVoivodeship());
        userCheck.setPostalCode(userRequest.getPostalCode());
        userCheck.setCountry(userRequest.getCountry());

        userRepository.save(userCheck);

        return userMapper.userToUserResponse(userCheck);
    }

    @Override
    public UserResponse getUser(Long id) {
      User userCheck = checkIfUserExists(id);

      return userMapper.userToUserResponse(userCheck);
    }


    private User checkIfUserExists(Long id){
        User userCheck = userRepository.findById(id).orElse(null);

        if (userCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }
        return userCheck;
    }
}
