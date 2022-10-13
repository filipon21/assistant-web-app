package edu.ib.webapp.user.service;

import edu.ib.webapp.user.model.dto.AssistantPaginationDto;
import edu.ib.webapp.user.model.request.UserRequest;
import edu.ib.webapp.user.model.request.UserUpdateRequest;
import edu.ib.webapp.user.model.response.AssistantListResponse;
import edu.ib.webapp.user.model.response.UserResponse;

public interface UserService {

    UserResponse registerNewUser(UserRequest user);

    UserResponse updateStatus(Boolean isOnline, Long id);

    UserResponse getUser(Long id);

    UserResponse updateUser(UserUpdateRequest user, Long id);

    AssistantListResponse getAllAssistantPaginated(AssistantPaginationDto templatePaginationDto);

}
