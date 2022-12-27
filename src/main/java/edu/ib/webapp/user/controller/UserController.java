package edu.ib.webapp.user.controller;

import edu.ib.webapp.common.pagination.SortingParamsDto;
import edu.ib.webapp.user.model.dto.AssistantPaginationDto;
import edu.ib.webapp.user.model.dto.AssistantSearchingParamsDto;
import edu.ib.webapp.user.model.dto.UserPaginationDto;
import edu.ib.webapp.user.model.dto.UserSearchingParamsDto;
import edu.ib.webapp.user.model.request.UserRequest;
import edu.ib.webapp.user.model.request.UserUpdateRequest;
import edu.ib.webapp.user.model.response.AssistantListResponse;
import edu.ib.webapp.user.model.response.UserListResponse;
import edu.ib.webapp.user.model.response.UserResponse;
import edu.ib.webapp.user.pagination.PaginationSupport;
import edu.ib.webapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Klasa do obsługi funkcjonalności związanej z użytkownikiem (kontroler Springowy)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Metoda służąca do obsługi zapytania dot. tworzenia nowego użytkownika
     * @param user - dane użytkownika
     * @return dane użytkownika
     */
    @PostMapping({"/register"})
    public UserResponse registerNewUser(@RequestBody @Valid UserRequest user) {
        return userService.registerNewUser(user);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. edycji danych użytkownika
     * @param id - id użytkownika
     * @param user - nowe dane użytkownika
     * @return dane użytkownika
     */
    @PatchMapping({"/{id}"})
    public UserResponse updateUser(@RequestBody @Valid UserUpdateRequest user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. ustawienia statusu użytkownika
     * @param isOnline - status użytkownika jako wartość fałsz/prawda czy online
     * @param id - id użytkownika
     * @return
     */
    @PatchMapping("{id}/online")
    public UserResponse updateStatus(@RequestParam @Valid Boolean isOnline, @PathVariable Long id){
        return userService.updateStatus(isOnline, id);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. pobrania danych o użytkowniku
     * @param id - id użytkownika
     * @return dane użytkownika
     */
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. zwrócenia listy wszystkich asystentów (filtracja, paginacja)
     * @param pageNumber - numer aktualnej strony
     * @param pageSize - aktualny rozmiar strony
     * @param sortParameter - parametr po którym jest sortowanie
     * @param sortDirection - kierunek sortowania (rosnoący (asc), malejacy (desc))
     * @param userFirstName - imie użytkownika
     * @param userLastName - nazwisko
     * @param phoneNumber - numer telefonu
     * @param isOnline - status
     * @return zwraca listę asystentów po ew. filtracji
     */
    @GetMapping("/assistant")
    public AssistantListResponse getAllAssistants(@RequestParam Integer pageNumber,
                                                 @RequestParam Integer pageSize,
                                                 @RequestParam(required = false) String sortParameter,
                                                 @RequestParam(required = false) String sortDirection,
                                                 @RequestParam(required = false) String userFirstName,
                                                 @RequestParam(required = false) String userLastName,
                                                 @RequestParam(required = false) String phoneNumber,
                                                 @RequestParam(required = false) Boolean isOnline)
    {

        SortingParamsDto sortingParams = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        AssistantSearchingParamsDto searchingParams = PaginationSupport.getAssistantSearchingParams(userFirstName, userLastName,
                phoneNumber, isOnline);
        AssistantPaginationDto paginationDto = PaginationSupport.getAssistantPaginationDto(pageNumber, pageSize, sortingParams,
                searchingParams);

        return userService.getAllAssistantPaginated(paginationDto);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. zwrócenia listy wszystkich użytkowników (filtracja, paginacja)
     * @param pageNumber - numer aktualnej strony
     * @param pageSize - aktualny rozmiar strony
     * @param sortParameter - parametr po którym jest sortowanie
     * @param sortDirection - kierunek sortowania (rosnoący (asc), malejacy (desc))
     * @param userFirstName - imie użytkownika
     * @param userLastName - nazwisko
     * @param phoneNumber - numer telefonu
     * @param pesel - pesel
     * @return zwraca listę użytkowników po paginacji i ew. filtracji
     */
    @GetMapping("/users")
    public UserListResponse getAllUsers(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(required = false) String sortParameter,
                                            @RequestParam(required = false) String sortDirection,
                                            @RequestParam(required = false) String userFirstName,
                                            @RequestParam(required = false) String userLastName,
                                            @RequestParam(required = false) String phoneNumber,
                                            @RequestParam(required = false) String pesel)
    {

        SortingParamsDto sortingParams = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        UserSearchingParamsDto searchingParams = PaginationSupport.getUserSearchingParams(userFirstName, userLastName,
                phoneNumber, pesel);
        UserPaginationDto paginationDto = PaginationSupport.getUserPaginationDto(pageNumber, pageSize, sortingParams,
                searchingParams);

        return userService.getAllUsersPaginated(paginationDto);
    }

}
