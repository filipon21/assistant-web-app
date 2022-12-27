package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.model.dto.AssistantInfoDto;
import edu.ib.webapp.user.model.dto.AssistantPaginationDto;
import edu.ib.webapp.common.configuration.PasswordEncoderCustom;
import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.mapper.UserMapper;
import edu.ib.webapp.user.model.dto.UserInfoDto;
import edu.ib.webapp.user.model.dto.UserPaginationDto;
import edu.ib.webapp.user.model.request.UserRequest;
import edu.ib.webapp.user.model.request.UserUpdateRequest;
import edu.ib.webapp.user.model.response.AssistantListResponse;
import edu.ib.webapp.user.model.response.UserListResponse;
import edu.ib.webapp.user.model.response.UserResponse;
import edu.ib.webapp.user.repository.RoleRepository;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.repository.specification.AssistantSpecification;
import edu.ib.webapp.user.repository.specification.UserSpecification;
import edu.ib.webapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static edu.ib.webapp.user.pagination.PaginationSupport.getPageRequest;

/**
 * Klasa służąca do przetworzenia logiki biznesowej związanej z funkcjonalnościami dotyczacych użytkownika (serwis Springowy)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoderCustom passwordEncoder;

    private final UserMapper userMapper;

    /**
     * Metoda służąca do tworzenia nowych użytkowników
     * @param userRequest - dane potrzebne do utworzenia użytkownika
     * @return nowy użytkownik (User)
     */
    @Transactional
    @Override
    public UserResponse registerNewUser(UserRequest userRequest) {
        User userCheck = userRepository.findByUserName(userRequest.getUserName()).orElse(null);

        if (userCheck != null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USERNAME_MUST_BE_UNIQUE);
        }

        Role role = roleRepository.findByRoleName("USER").orElse(null);
        if (role == null){
            throw new UserException(HttpStatus.NOT_FOUND, ExceptionMessage.ROLE_NOT_FOUND);
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
        user.setTown(userRequest.getTown());
        user.setBirthday(userRequest.getBirthday());
        user.setAge(ChronoUnit.YEARS.between(userRequest.getBirthday(), LocalDate.now()));
        user.setUserPassword(passwordEncoder.getEncodedPassword(userRequest.getUserPassword()));
        user.setAddress(userRequest.getAddress());
        user.setVoivodeship(userRequest.getVoivodeship());
        user.setPostalCode(userRequest.getPostalCode());
        user.setCountry(userRequest.getCountry());

        userRepository.save(user);

        return userMapper.userToUserResponse(user);
    }

    /**
     * Metoda służąca do edycji statusu użytkownika
     * @param isOnline - status czy online
     * @param id - id użytkownika (bazodanowe)
     * @return zwraca użytkownika (User)
     */
    @Override
    @Transactional
    public UserResponse updateStatus(Boolean isOnline, Long id) {
        User userCheck = checkIfUserExists(id);

        userCheck.setIsOnline(isOnline);

        userRepository.save(userCheck);

        return userMapper.userToUserResponse(userCheck);
    }

    /**
     * Metoda służąca do edycji użytkownika
     * @param userRequest - dane potrzebne do edycji
     * @param id - id użytkownika (bazodanowe)
     * @return zwraca użytkownika (User)
     */
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
        userCheck.setTown(userRequest.getTown());
        userCheck.setVoivodeship(userRequest.getVoivodeship());
        userCheck.setPostalCode(userRequest.getPostalCode());
        userCheck.setCountry(userRequest.getCountry());

        userRepository.save(userCheck);

        return userMapper.userToUserResponse(userCheck);
    }

    /**
     * Metoda służąca do zwrócenia wszystkich asystentów posortowanych i po filtracji
     * @param assistantPaginationDto - dane potrzebne do filtracji, sortowania, paginacji
     * @return lista spaginowanych asystentów
     */
    @Override
    public AssistantListResponse getAllAssistantPaginated(AssistantPaginationDto assistantPaginationDto) {
        AssistantSpecification assistantSpecification = new AssistantSpecification(assistantPaginationDto.getSearchingParams());
        PageRequest assistantPageRequest = getPageRequest(assistantPaginationDto);
        Page<User> assistantPage = userRepository.findAll(assistantSpecification, assistantPageRequest);
        List<AssistantInfoDto> assistantInfoDtos = assistantPage.stream().map(userMapper::userToAssistantInfoDto)
                .collect(Collectors.toList());

        log.info("Poprawne pobranie listy asystentów");

        Page<AssistantInfoDto> assistantInfoDtoPage = new PageImpl(assistantInfoDtos, assistantPage.getPageable(),
                assistantPage.getTotalElements());
        return new AssistantListResponse(assistantInfoDtoPage);
    }

    /**
     * Metoda służąca do zwrócenia wszystkich użytkowników posortowanych i po filtracji
     * @param userPaginationDto - dane potrzebne do filtracji, sortowania, paginacji
     * @return lista spaginowanych użytkowników
     */
    @Override
    public UserListResponse getAllUsersPaginated(UserPaginationDto userPaginationDto) {
        UserSpecification userSpecification = new UserSpecification(userPaginationDto.getSearchingParams());
        PageRequest userPageRequest = getPageRequest(userPaginationDto);
        Page<User> userPage = userRepository.findAll(userSpecification, userPageRequest);
        List<UserInfoDto> userInfoDtos = userPage.stream()
                .map(userMapper::userToUserInfoDto).collect(Collectors.toList());
        Page<AssistantInfoDto> userInfoDtoPage = new PageImpl(userInfoDtos, userPage.getPageable(),
                userPage.getTotalElements());
        return new UserListResponse(userInfoDtoPage);
    }

    /**
     * Metoda służąca do zwrócenia danego użytkownika po id
     * @param id - id użytkownika (bazodanowe)
     * @return użytkownik (User)
     */
    @Override
    public UserResponse getUser(Long id) {
      User userCheck = checkIfUserExists(id);

      return userMapper.userToUserResponse(userCheck);
    }

    /**
     * Metoda służąca do sprawdzenia czy użytkownik istnieje w bazie danych
     * @param id - id użytkownika (bazodanowe)
     * @return użytkownik (User)
     */
    private User checkIfUserExists(Long id){
        User userCheck = userRepository.findById(id).orElse(null);

        if (userCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }
        return userCheck;
    }

}
