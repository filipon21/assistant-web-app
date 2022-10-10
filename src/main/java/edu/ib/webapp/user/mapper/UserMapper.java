package edu.ib.webapp.user.mapper;

import edu.ib.webapp.api.model.dto.AssistantInfoDto;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.model.response.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    AssistantInfoDto userToAssistantInfoDto(User user);
}
