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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping({"/register"})
    public UserResponse registerNewUser(@RequestBody @Valid UserRequest user) {
        return userService.registerNewUser(user);
    }

    @PatchMapping({"/{id}"})
    public UserResponse updateUser(@RequestBody @Valid UserUpdateRequest user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @PatchMapping("{id}/online")
    public UserResponse updateStatus(@RequestParam @Valid Boolean isOnline, @PathVariable Long id){
        return userService.updateStatus(isOnline, id);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

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
