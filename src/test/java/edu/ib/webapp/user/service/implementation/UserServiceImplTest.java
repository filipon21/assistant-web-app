package edu.ib.webapp.user.service.implementation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ib.webapp.common.configuration.PasswordEncoderCustom;
import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Assistant;
import edu.ib.webapp.user.entity.Doctor;
import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.enums.AssistantSpecializationEnum;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.mapper.UserMapper;
import edu.ib.webapp.user.model.request.UserRequest;
import edu.ib.webapp.user.model.response.UserResponse;
import edu.ib.webapp.user.repository.RoleRepository;
import edu.ib.webapp.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private PasswordEncoderCustom passwordEncoderCustom;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#registerNewUser(UserRequest)}
     */
    @Test
    void testRegisterNewUser() {
        User user = new User();
        user.setId(123L);
        user.setIsActive(true);
        user.setIsOnline(true);
        user.setPhoneNumber("4105551212");
        user.setRoles(new HashSet<>());
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUserName((String) any())).thenReturn(ofResult);
        assertThrows(UserException.class, () -> userServiceImpl.registerNewUser(new UserRequest()));
        verify(userRepository).findByUserName((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#registerNewUser(UserRequest)}
     */
    @Test
    void testRegisterNewUser6() {
        User user = new User();
        user.setAddress("42 Main St");
        user.setAge(1L);
        user.setBirthday(LocalDate.ofEpochDay(1L));
        user.setCountry("GB");
        user.setId(123L);
        user.setIsActive(true);
        user.setIsOnline(true);
        user.setPesel("Pesel");
        user.setPhoneNumber("4105551212");
        user.setPostalCode("Postal Code");
        user.setRoles(new HashSet<>());
        user.setTown("Oxford");
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("dfgd");
        user.setVoivodeship("Dolno");
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByUserName((String) any())).thenReturn(Optional.empty());

        Role role = new Role();
        role.setId(123L);
        role.setRoleDescription("Role Description");
        role.setRoleName("Role Name");
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByRoleName((String) any())).thenReturn(ofResult);
        when(passwordEncoderCustom.getEncodedPassword((String) any())).thenReturn("secret");
        UserResponse userResponse = new UserResponse();
        when(userMapper.userToUserResponse((User) any())).thenReturn(userResponse);

        UserRequest userRequest = new UserRequest();
        userRequest.setBirthday(LocalDate.ofEpochDay(59L));
        assertSame(userResponse, userServiceImpl.registerNewUser(userRequest));
        verify(userRepository).save((User) any());
        verify(userRepository).findByUserName((String) any());
        verify(roleRepository).findByRoleName((String) any());
        verify(passwordEncoderCustom).getEncodedPassword((String) any());
    }
}

